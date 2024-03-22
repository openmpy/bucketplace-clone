package com.example.bucketplace.domain.product.scheduler;

import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ProductScheduler {

    private static final String BUCKETPLACE_SHOP_URL = "https://ohou.se/store";
    private final ProductRepository productRepository;

    public ProductScheduler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000) // 한 시간 마다 반복
    public void bucketplaceCrawling() throws InterruptedException {
        WebDriver webDriver = setupWebDriver();
        try {
            webDriver.get(BUCKETPLACE_SHOP_URL);
            Thread.sleep(3000); // 페이지 로드 대기

            List<Product> products = crawlProducts(webDriver);
            productRepository.saveAll(products);
        } finally {
            webDriver.close();
            webDriver.quit();
        }
        log.info("오늘의집 크롤링 성공");
    }

    private WebDriver setupWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(chromeOptions);
    }

    private List<Product> crawlProducts(WebDriver webDriver) throws InterruptedException {
        List<Product> productList = new ArrayList<>();
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        for (int i = 0; i < 2; i++) {
            javascriptExecutor.executeScript("window.scrollBy(0, 5000)");
            Thread.sleep(3000);

            List<WebElement> elementList = webDriver.findElements(By.cssSelector(".production-item"));
            for (WebElement webElement : elementList) {
                Product product = createProductFromWebElement(webElement, wait);
                if (product != null) {
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    private Product createProductFromWebElement(WebElement webElement, WebDriverWait wait) {
        String productName = webElement.findElement(By.cssSelector(".production-item__header__name")).getText();

        if (productRepository.existsByName(productName)) {
            return null;
        }

        String brandName = webElement.findElement(By.cssSelector(".production-item__header__brand")).getText();

        Optional<WebElement> discountElement = safelyFindElement(webElement, By.cssSelector(".production-item-price__rate"));
        String discount = discountElement.map(WebElement::getText).orElse("0%");

        String price = webElement.findElement(By.cssSelector(".production-item-price__price")).getText().replace(",", "");

        boolean isFreeDelivery = safelyFindElement(webElement, By.xpath("//*[@aria-label='무료배송']")).isPresent();
        boolean isSpecialPrice = safelyFindElement(webElement, By.xpath("//*[@aria-label='특가']")).isPresent();

        WebElement imageElement = wait.until(ExpectedConditions.visibilityOf(webElement.findElement(By.cssSelector(".image"))));
        String imageUrl = imageElement.getAttribute("src");

        return Product.builder()
                .brand(brandName)
                .name(productName)
                .discount(Double.parseDouble(discount.replace("%", "")))
                .price(Long.parseLong(price))
                .imageUrl(imageUrl)
                .isFreeDelivery(isFreeDelivery)
                .isSpecialPrice(isSpecialPrice)
                .build();
    }

    private Optional<WebElement> safelyFindElement(WebElement webElement, By by) {
        try {
            return Optional.ofNullable(webElement.findElement(by));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
