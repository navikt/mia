package no.nav.fo.mia.util;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringToListConverter implements Converter<String, List<String>> {
    @Override
    public List<String> convert(String source) {
        return Collections.singletonList(source);
    }
}
