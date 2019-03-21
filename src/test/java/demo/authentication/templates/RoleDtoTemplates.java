package demo.authentication.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import demo.authentication.dto.RoleDto;

public class RoleDtoTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(RoleDto.class).addTemplate("USER", new Rule() {{
            add("name", "ROLE_USER");
        }});

        Fixture.of(RoleDto.class).addTemplate("ADMIN", new Rule() {{
            add("name", "ROLE_ADMIN");
        }});
    }
}
