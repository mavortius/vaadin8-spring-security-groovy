package com.company.demo.ui

import com.company.demo.security.SecurityContextUtils
import com.company.demo.view.AccessDeniedView
import com.company.demo.view.AdminView
import com.company.demo.view.ErrorView
import com.company.demo.view.UserView
import com.vaadin.annotations.Theme
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewDisplay
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.spring.annotation.SpringViewDisplay
import com.vaadin.spring.navigator.SpringNavigator
import com.vaadin.spring.navigator.SpringViewProvider
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.CssLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.vaadin.spring.security.VaadinSecurity

import javax.annotation.PostConstruct
import java.time.LocalDateTime

@CompileStatic
@Theme(ValoTheme.THEME_NAME)
@SpringUI(path = "/")
@SpringViewDisplay
class MainUI extends UI implements ViewDisplay {

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    VaadinSecurity security

    @Autowired
    SpringViewProvider viewProvider

    @Autowired
    SpringNavigator navigator

    Panel display

    @PostConstruct
    void init() {
        navigator.errorView = ErrorView
        viewProvider.accessDeniedViewClass = AccessDeniedView
        display = new Panel()
        display.setSizeFull()
    }

    @Override
    void showView(View view) {
        display.content = view as Component
    }

    @Override
    protected void init(VaadinRequest request) {
        page.title = 'Vaadin Security Demo'
        final CssLayout navigationBar = new CssLayout()
        
        navigationBar.with {
            addStyleName ValoTheme.LAYOUT_COMPONENT_GROUP
            addComponent createNavigationButton('User View', UserView.VIEW_NAME)
            addComponent createNavigationButton('Admin View', AdminView.VIEW_NAME)
            addComponent new Button('Logout', { security.logout() } as Button.ClickListener)
        }

        final VerticalLayout root = new VerticalLayout()
        
        root.with {
            setSizeFull()
            addComponents new Label("${SecurityContextUtils.user.username} : ${LocalDateTime.now()}")
            addComponent navigationBar
            addComponent display
            setExpandRatio display, 1.0f
        }

        content = root
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption)

        button.addClickListener { event -> getUI().navigator.navigateTo(viewName) }

        button
    }
}
