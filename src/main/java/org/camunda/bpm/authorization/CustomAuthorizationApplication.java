package org.camunda.bpm.authorization;

import org.camunda.bpm.authorization.custom.CustomLdapIdentityProviderPlugin;
import org.camunda.bpm.authorization.custom.CustomProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class CustomAuthorizationApplication {

  @Bean
  @Primary
  @Order(Integer.MAX_VALUE - 3)
  public ProcessEnginePlugin strongUUIDGenerator() {
    return new CustomProcessEnginePlugin();
  }

  @Bean
  @Primary
  @Order(Integer.MAX_VALUE - 2)
  public LdapIdentityProviderPlugin ldapIdentityProviderPlugin() {
    final LdapIdentityProviderPlugin plugin = new CustomLdapIdentityProviderPlugin();

    plugin.setServerUrl("ldap://localhost:389");
    plugin.setAcceptUntrustedCertificates(true);
    plugin.setManagerDn("cn=admin,dc=example,dc=org");
    plugin.setManagerPassword("admin");

    plugin.setBaseDn("dc=example,dc=org");

    plugin.setUserSearchFilter("(objectclass=person)");

    plugin.setUserFirstnameAttribute("cn");
    plugin.setUserLastnameAttribute("sn");
    plugin.setUserEmailAttribute("mail");
    plugin.setUserPasswordAttribute("userPassword");

    plugin.setGroupSearchFilter("(objectclass=groupOfNames)");
    plugin.setGroupIdAttribute("cn");
    plugin.setGroupNameAttribute("cn");
    plugin.setGroupMemberAttribute("member");

    return plugin;
  }

  @Bean
  @Primary
  @Order(Integer.MAX_VALUE - 1)
  public AdministratorAuthorizationPlugin administratorAuthorizationPlugin() {
    final AdministratorAuthorizationPlugin plugin = new AdministratorAuthorizationPlugin();
    plugin.setAdministratorUserName("camunda");
    return plugin;
  }

  public static void main(final String... args) {
    SpringApplication.run(CustomAuthorizationApplication.class, args);
  }

}