package org.giste.club.server;

import org.giste.spring.util.locale.LocaleMessage;
import org.giste.spring.util.locale.LocaleMessageImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Spring configuration for localization. Localization is made using session
 * resolver looking for param "lang".
 * 
 * @author Giste
 */
@Configuration
public class LocaleConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * Provides the message source bean for localizing messages.
	 * 
	 * @return The MessageSource bean.
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasenames("classpath:i18n/messages", "classpath:i18n/errors");
		source.setFallbackToSystemLocale(false);
		source.setDefaultEncoding("UTF-8");
		source.setUseCodeAsDefaultMessage(true);

		return source;
	}

	/**
	 * Provides the locale resolver bean. Uses SessionLocaleResolver.
	 * 
	 * @return The LocaleResolver bean.
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();

		return sessionLocaleResolver;
	}

	/**
	 * Provides the bean for intercept locale changes. Looks for param "lang".
	 * 
	 * @return The LocaleChangeInterceptor bean.
	 */
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");

		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * Gets the bean for retrieving localized messages.
	 * 
	 * @return The {@link LocaleMessage} bean.
	 */
	@Bean
	public LocaleMessage localeMessage() {
		return new LocaleMessageImpl(this.messageSource());
	}

}
