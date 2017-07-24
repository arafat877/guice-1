package org.mybatis.guice.configuration.settings;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;

import javax.inject.Provider;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;

public final class JavaTypeAndHandlerConfigurationSettingProvider<T> implements Provider<ConfigurationSetting> {
  @Inject private Injector injector;

  private final Class<T> type;
  private final Key<? extends TypeHandler<? extends T>> key;
  
  public JavaTypeAndHandlerConfigurationSettingProvider(final Class<T> type, final Key<? extends TypeHandler<? extends T>> key) {
    this.type = type;
    this.key = key;
  }

  @Override
  public ConfigurationSetting get() {
    final TypeHandler<? extends T> handlerInstance = injector.getInstance(key);
    return new ConfigurationSetting() {
      @Override
      public void applyConfigurationSetting(Configuration configuration) {
        configuration.getTypeHandlerRegistry().register(type, handlerInstance);
      }
    };
  }

}