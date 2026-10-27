package codegym.vn;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Cấu hình tầng web/MVC (message converters, i18n, view, upload).
 * Cấu hình persistence (DataSource, JPA, transaction) nằm ở {@link PersistenceConfig}.
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        XmlMapper xmlMapper = new XmlMapper();
        converters.add(new MappingJackson2XmlHttpMessageConverter(xmlMapper));
    }
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource =
                new ResourceBundleMessageSource();

        messageSource.setBasename("message");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new
                CommonsMultipartResolver();
        multipartResolver.setMaxUploadSizePerFile(5*1024*1024);
        return multipartResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor)
                .addPathPatterns("/**");

    }

//    @Bean
//    public LocaleResolver localeResolver() {
    // TODO SessionLocaleResolver kết hợp với request param
    // TODO AcceptHeaderLocaleResolver kết hợp với request headers
//        SessionLocaleResolver resolver =
//                new SessionLocaleResolver();
//
//        resolver.setDefaultLocale(Locale.ENGLISH);
//
//        return resolver;
//    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver =
                new SessionLocaleResolver();

        resolver.setDefaultLocale(Locale.ENGLISH);

        return resolver;
    }
}
