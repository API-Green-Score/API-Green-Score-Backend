package fr.apithinking.apigreenscore.services.impl;

import fr.apithinking.apigreenscore.exception.NotFoundRuleException;
import fr.apithinking.apigreenscore.mapper.ApiGreenscoreMapper;
import fr.apithinking.apigreenscore.model.Rule;
import fr.apithinking.apigreenscore.provider.mongo.RuleRepository;
import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import fr.apithinking.apigreenscore.services.RulesService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RuleServiceImpl implements RulesService {

    private final ApiGreenscoreMapper mapper;

    private final RuleRepository ruleRepository;

    @Override
    public Page<Rule> getRules(Integer page, Integer size) {

        long rulesTotalCount = -1;
        List<Rule> rules = new ArrayList<>();

        if (size <= 0) {
            rulesTotalCount = ruleRepository.count();
            return new PageImpl<>(rules, Pageable.unpaged(), rulesTotalCount);
        }

        List<RuleMongo> rulesMongo = ruleRepository.findAll(PageRequest.of(page, size)).getContent();
        if (CollectionUtils.isNotEmpty(rulesMongo)) {
            for (RuleMongo ruleMongo : rulesMongo) {
                Rule rule = mapper.buildRule(ruleMongo);
                rules.add(rule);
            }
        }

        return new PageImpl<>(rules, PageRequest.of(page, size), rulesTotalCount);
    }

    @Override
    public Rule getRule(final String pId) {
        return mapper.buildRule(
                ruleRepository.findById(pId)
                        .orElseThrow(() -> new NotFoundRuleException("id", pId)));
    }
}
