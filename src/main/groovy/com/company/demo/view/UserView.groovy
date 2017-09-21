package com.company.demo.view

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import groovy.transform.CompileStatic

import javax.annotation.PostConstruct

@CompileStatic
@SpringView(name = UserView.VIEW_NAME)
class UserView extends VerticalLayout implements View {
    public final static String VIEW_NAME = ''

    @PostConstruct
    void init() {
        addComponent new Label('Hello, this is user view.')
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        // View created on init() method.
    }
}
