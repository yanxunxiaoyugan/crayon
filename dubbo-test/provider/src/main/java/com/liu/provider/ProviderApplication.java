package com.liu.provider;

//import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 *假设我们有数字+大小写字母组成的图片，每个图片上都是10位字符，
 * 所有字符都是随机出现，假设我们有一个OCR（光学文字识别算法），
 * 对大小写字母识别的准确率是95%，对阿拉伯数字识别的准确率是99%，
 * 那么对于一个随机图片，完全识别对的概率是多少
 *
 * 95%
 *
 *
 * 62   10
 * 10
 * 10/62  52/62
 *
 *
 * （10/62 * 99%）6+  52/62 * 95
 *
 *
 * C10 1  10/62 * 99%      C52/62 * 95
 *
 *
 *
 */
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        String[] a = new String[2];
        Object[] b = a;
        a[0] = "hi";
        b[1] = 42;

        SpringApplication.run(ProviderApplication.class, args);
    }
}
