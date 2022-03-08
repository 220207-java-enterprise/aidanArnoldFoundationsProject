package com.revature.foundation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundation.daos.ReimbursementsDAO;
import com.revature.foundation.daos.UsersDAO;
import com.revature.foundation.services.ReimbursementService;
import com.revature.foundation.services.TokenService;
import com.revature.foundation.services.UserService;
import com.revature.foundation.servlets.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextLoaderListener implements ServletContextListener {

    private static Logger logger = LogManager.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Initializing foundation web application");

        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);
        ReimbursementsDAO reimbursementsDAO = new ReimbursementsDAO();
        UsersDAO userDAO = new UsersDAO();
        UserService userService = new UserService(userDAO);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementsDAO);

        UsersServlet userServlet = new UsersServlet(tokenService, userService, mapper);
        AuthServlet authServlet = new AuthServlet(tokenService, userService, mapper);
        AdminUserUpdateServlet adminUserUpdateServlet = new AdminUserUpdateServlet(tokenService, userService, mapper);
        UpdatedReimbursementServlet updatedReimbursementServlet = new UpdatedReimbursementServlet(tokenService, reimbursementService, mapper);
        NoLogInServlet noLogInServlet = new NoLogInServlet(tokenService);
        UserMakeReimbursementServlet userMakeReimbursementServlet = new UserMakeReimbursementServlet(tokenService, reimbursementService, mapper);
        AllReimbursementsByIdServlet allReimbursementsByIdServlet = new AllReimbursementsByIdServlet(tokenService, reimbursementService, mapper);

        // Programmatic Servlet Registration
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("AdminUserUpdateServlet", adminUserUpdateServlet).addMapping("/adminUserUpdate");
        context.addServlet("NoLogInServlet", noLogInServlet).addMapping("/noLogInServlet");
        context.addServlet("UpdatedReimbursementServlet", updatedReimbursementServlet).addMapping("/updatedReimbursement");
        context.addServlet("UserMakeReimbursementServlet", userMakeReimbursementServlet).addMapping("/newReimbursement");
        context.addServlet("AllReimbursementsByIdServlet", allReimbursementsByIdServlet).addMapping("/allReimbursementsById");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Shutting down foundation web application");
    }

}
