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
class AccessDeniedView extends VerticalLayout implements View {

    @PostConstruct
    void init() {
        Label label = new Label('this is AccessDeniedView.')
        label.addStyleName ValoTheme.LABEL_FAILURE
        addComponent label
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        // View created on init() method.
    }
}
