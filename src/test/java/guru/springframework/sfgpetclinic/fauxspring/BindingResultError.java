package guru.springframework.sfgpetclinic.fauxspring;

import java.util.HashMap;
import java.util.Map;

public class BindingResultError implements BindingResult {

    private Map<String,String> map = new HashMap<>();

    @Override
    public void rejectValue(String lastName, String notFound, String not_found) {
        map.put(lastName,notFound);
    }

    @Override
    public boolean hasErrors() {
        return map.size() > 0;
    }
}
