package demo.configuration;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsExecutorConfig {

    @Bean
    public KnowledgeBuilder knowledgeBuilder() throws Exception {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("rules/ok-rule.drl"), ResourceType.DRL);
        builder.add(ResourceFactory.newClassPathResource("rules/evacuation-rule.drl"), ResourceType.DRL);
        builder.add(ResourceFactory.newClassPathResource("rules/attention-rule.drl"), ResourceType.DRL);
        if (builder.hasErrors()) {
            throw new Exception(String.valueOf(builder.getErrors()));
        }
        return builder;
    }

    @Bean
    public InternalKnowledgeBase knowledgeBase(KnowledgeBuilder knowledgeBuilder) throws Exception {
        InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addPackages(knowledgeBuilder.getKnowledgePackages());
        return knowledgeBase;
    }
}
