package no.nav.fo.mia.util;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class StringToListConverter implements Converter<String, List<String>> {
    @Override
    public List<String> convert(String source) {
        ArrayList<String> list = new ArrayList<>();
        list.add(source);
        return list;
    }
}
