package demo.authentication.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import demo.authentication.dto.UserDto;

public class UserDtoTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(UserDto.class).addTemplate("VALID", new Rule() {{
            add("username", "test");
            add("password", "test");
        }});
    }
}
