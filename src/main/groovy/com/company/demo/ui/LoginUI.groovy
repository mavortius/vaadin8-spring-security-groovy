package com.company.demo.ui

import com.vaadin.annotations.Theme
import com.vaadin.event.ShortcutAction
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.CheckBox
import com.vaadin.ui.FormLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Notification
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.vaadin.spring.security.shared.VaadinSharedSecurity

@CompileStatic
@Slf4j
@SpringUI(path = '/login')
@Theme(ValoTheme.THEME_NAME)
class LoginUI extends UI {

    @Autowired
    VaadinSharedSecurity security

    private TextField userName

    private PasswordField passwordField

    private CheckBox rememberMe

    private Button login

    private Label loginFailedLabel
    private Label loggedOutLabel

    @Override
    protected void init(VaadinRequest request) {
        page.title = 'Vaadin Security Demo Login'
        FormLayout loginForm = new FormLayout()
        loginForm.setSizeUndefined()

        userName = new TextField('Username')
        passwordField = new PasswordField('Password')
        rememberMe = new CheckBox('Remember me')
        login = new Button('Login')

        loginForm.with {
            addComponent(userName)
            addComponent(passwordField)
            addComponent(rememberMe)
            addComponent(login)
        }
        
        login.with {
            addStyleName ValoTheme.BUTTON_PRIMARY
            disableOnClick = true
            setClickShortcut ShortcutAction.KeyCode.ENTER
            addClickListener { login() }
        }

        VerticalLayout loginLayout = new VerticalLayout()
        
        loginLayout.with {
            spacing = true
            setSizeUndefined()
        }

        if (request.getParameter('logout')) {
            loggedOutLabel = new Label('You have been logged out!')

            loggedOutLabel.with {
                addStyleName ValoTheme.LABEL_SUCCESS
                setSizeUndefined()
            }

            loginLayout.with {
                addComponent loggedOutLabel
                setComponentAlignment loggedOutLabel, Alignment.BOTTOM_CENTER
            }
        }

        loginLayout.with {
            addComponent(loginFailedLabel = new Label())
            setComponentAlignment loginFailedLabel, Alignment.BOTTOM_CENTER
        }

        loginFailedLabel.with {
            setSizeUndefined()
            addStyleName ValoTheme.LABEL_FAILURE
            visible = false
        }

        loginLayout.with {
            addComponent loginForm
            setComponentAlignment loginForm, Alignment.TOP_CENTER
        }

        VerticalLayout rootLayout = new VerticalLayout(loginLayout)

        rootLayout.with {
            setSizeFull()
            setComponentAlignment loginLayout, Alignment.MIDDLE_CENTER
        }

        content = rootLayout
        setSizeFull()
    }

    private void login() {
        try {
            security.login userName.value, passwordField.value, rememberMe.value
        } catch (AuthenticationException ex) {
            userName.focus()
            userName.selectAll()

            passwordField.value = ''
            loginFailedLabel.value = "Login failed: ${ex.message}"
            loginFailedLabel.visible = true

            if (loggedOutLabel) {
                loggedOutLabel.visible = false
            }
        } catch (Exception ex) {
            Notification.show 'An unexpected error occurred', ex.message, Notification.Type.ERROR_MESSAGE
            log.error 'Unexpected error while logging in', ex
        } finally {
            login.enabled = true
        }
    }
}
