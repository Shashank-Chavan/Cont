package com.example.contacts.Dagger_Implementation;

import com.example.contacts.Contact_Page;
import com.example.contacts.VM;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(VM vm);
}
