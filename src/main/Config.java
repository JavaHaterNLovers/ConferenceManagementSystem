package main;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.spring.JtwigViewResolver;
import org.jtwig.spring.asset.SpringAssetExtension;
import org.jtwig.spring.asset.function.JtwigAssetFunction;
import org.jtwig.spring.asset.resolver.AssetResolver;
import org.jtwig.spring.asset.resolver.BaseAssetResolver;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config
{
    @Bean
    public JtwigViewResolver viewResolver() {
        BaseAssetResolver assetResolver = new BaseAssetResolver();
        assetResolver.setPrefix("resources");
        BaseAssetResolver pathResolver = new BaseAssetResolver();
        pathResolver.setPrefix("/CMSWeb");

        JtwigViewResolver resolver = new JtwigViewResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html.twig");
        resolver.setRenderer(new JtwigRenderer(EnvironmentConfigurationBuilder.configuration()
                .extensions().add(new SpringAssetExtension(){
                    @Override
                    public void configure(EnvironmentConfigurationBuilder configurationBuilder) {
                        configurationBuilder
                                .functions()
                                    .add(new JtwigAssetFunction() {
                                        @Override
                                        protected AssetResolver getAssetResolver () {
                                            return assetResolver;
                                        }
                                    })
                                    .add(new SimpleJtwigFunction() {
                                        @Override
                                        public String name() {
                                            return "path";
                                        }

                                        @Override
                                        public Object execute(FunctionRequest request) {
                                            request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);
                                            String path = request.get(0).toString();

                                            return pathResolver.resolve(path);
                                        }
                                    })
                                .and();
                    }
                }).and()
                .build()));
        return resolver;
    }
}
