

package vn.chapp.vn24h.di.component;

import vn.chapp.vn24h.di.PerService;
import vn.chapp.vn24h.di.module.ServiceModule;

import dagger.Component;


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
}
