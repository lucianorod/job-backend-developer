package demo.authentication.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import demo.authentication.model.User;

public class UserTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(User.class).addTemplate("VALID", new Rule() {{
            add("username", "test");
            add("password", "test");
        }});
    }
}
