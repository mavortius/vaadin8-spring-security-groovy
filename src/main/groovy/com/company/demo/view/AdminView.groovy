package com.company.demo.view

import com.company.demo.security.User
import com.company.demo.security.UserRepository
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Grid
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured

import javax.annotation.PostConstruct

@Secured(['ROLE_ADMIN'])
@SpringView(name = AdminView.VIEW_NAME)
class AdminView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "admin"

    @Autowired
    private UserRepository userRepository

    @PostConstruct
    void init() {
        addComponent new Label('Hello, this is admin view.')

        List<User> users = userRepository.findAll()
        Grid<User> grid = new Grid<>()

        grid.with {
            setSizeFull()
            items = users
            addColumn(User.&getUsername as String).caption = 'Name'
            addColumn(User.&getPassword as String).caption = 'Password'
        }

        addComponent grid
        setExpandRatio grid, 1f
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        // View created on init() method.
    }
}
