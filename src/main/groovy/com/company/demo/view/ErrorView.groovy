package com.company.demo.view

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.SpringView
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import groovy.transform.CompileStatic

import javax.annotation.PostConstruct

@CompileStatic
@SpringComponent
@UIScope
@SpringView
class ErrorView extends VerticalLayout implements View {


    @PostConstruct
    void init() {
        Label label = new Label("Hello, this is the 'error view' loaded if no view is matched based on URL.")
        label.addStyleName ValoTheme.LABEL_FAILURE
        addComponent label
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        // View created on init() method.
    }

}
