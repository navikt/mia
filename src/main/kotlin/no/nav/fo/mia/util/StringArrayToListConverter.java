package no.nav.fo.mia.util;

import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.List;

public class StringArrayToListConverter implements Converter<String[], List<String>> {
    @Override
    public List<String> convert(String[] source) {
        return Arrays.asList(source);
    }
}
