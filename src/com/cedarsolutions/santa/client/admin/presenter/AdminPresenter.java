/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013 Kenneth J. Pronovici.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Apache License, Version 2.0.
 * See LICENSE for more information about the licensing terms.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Author   : Kenneth J. Pronovici <pronovic@ieee.org>
 * Language : Java 6
 * Project  : Secret Santa Exchange
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.santa.client.admin.presenter;

import com.cedarsolutions.client.gwt.event.UnifiedEvent;
import com.cedarsolutions.client.gwt.handler.AbstractViewEventHandler;
import com.cedarsolutions.client.gwt.module.presenter.ModuleTemplatePresenter;
import com.cedarsolutions.santa.client.admin.AdminEventBus;
import com.cedarsolutions.santa.client.admin.view.AdminView;
import com.cedarsolutions.santa.client.admin.view.IAdminView;
import com.cedarsolutions.santa.client.common.presenter.SystemStateInjector;
import com.cedarsolutions.santa.shared.domain.ClientSession;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;

/**
 * Presenter for this module's template view.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
@Presenter(view = AdminView.class)
public class AdminPresenter extends ModuleTemplatePresenter<IAdminView, AdminEventBus> {

    /** Injector for the shared client session singleton. */
    private SystemStateInjector systemStateInjector;

    /** Handle the start event so that the view gets bound. */
    public void onStart() {
    }

    /** Bind the user interface. */
    @Override
    public void bind() {
        this.view.setInternalLandingPageEventHandler(new InternalLandingPageEventHandler(this));
        this.view.setDashboardEventHandler(new DashboardEventHandler(this));
        this.view.setAboutEventHandler(new AboutEventHandler(this));
        this.view.setBugReportEventHandler(new BugReportEventHandler(this));
        this.view.setSourceCodeEventHandler(new SourceCodeEventHandler(this));
        this.view.setLogoutEventHandler(new LogoutEventHandler(this));
    }

    /**
     * Replace the module body, rendering the passed-in widget.
     * @param contents  Widget contents to render
     */
    @Override
    public void onReplaceModuleBody(IsWidget contents) {
        this.view.setCurrentUser(this.getSession().getCurrentUser());
        super.onReplaceModuleBody(contents);
    }

    /** Get the session from the injector. */
    public ClientSession getSession() {
        return this.systemStateInjector.getSession();
    }

    public SystemStateInjector getSystemStateInjector() {
        return this.systemStateInjector;
    }

    @Inject
    public void setSystemStateInjector(SystemStateInjector systemStateInjector) {
        this.systemStateInjector = systemStateInjector;
    }

    /** Event handler for the internal landing page action. */
    protected static class InternalLandingPageEventHandler extends AbstractViewEventHandler<AdminPresenter> {
        public InternalLandingPageEventHandler(AdminPresenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.getParent().getEventBus().showInternalLandingPage();
        }
    }

    /** Event handler for the dashboard action. */
    protected static class DashboardEventHandler extends AbstractViewEventHandler<AdminPresenter> {
        public DashboardEventHandler(AdminPresenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.getParent().getEventBus().showApplicationDashboard();
        }
    }

    /** Event handler for the about action. */
    protected static class AboutEventHandler extends AbstractViewEventHandler<AdminPresenter> {
        public AboutEventHandler(AdminPresenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.getParent().getEventBus().showAboutPopup();
        }
    }

    /** Event handler for the bug report action. */
    protected static class BugReportEventHandler extends AbstractViewEventHandler<AdminPresenter> {
        public BugReportEventHandler(AdminPresenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.getParent().getEventBus().showBugReportDialog();
        }
    }

    /** Event handler for the source code action. */
    protected static class SourceCodeEventHandler extends AbstractViewEventHandler<AdminPresenter> {
        public SourceCodeEventHandler(AdminPresenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.getParent().getEventBus().showSourceCode();
        }
    }

    /** Event handler for logout action. */
    protected static class LogoutEventHandler extends AbstractViewEventHandler<AdminPresenter> {
        public LogoutEventHandler(AdminPresenter parent) {
            super(parent);
        }

        @Override
        public void handleEvent(UnifiedEvent event) {
            this.getParent().getEventBus().logout();
        }
    }
}
