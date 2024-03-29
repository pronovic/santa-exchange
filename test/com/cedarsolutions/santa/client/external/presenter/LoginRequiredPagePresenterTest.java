/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013,2015 Kenneth J. Pronovici.
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
package com.cedarsolutions.santa.client.external.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.cedarsolutions.santa.client.common.presenter.SystemStateInjector;
import com.cedarsolutions.santa.client.external.ExternalEventBus;
import com.cedarsolutions.santa.client.external.presenter.LoginRequiredPagePresenter.LoginEventHandler;
import com.cedarsolutions.santa.client.external.view.ILoginRequiredPageView;
import com.cedarsolutions.santa.client.junit.StubbedClientTestCase;
import com.cedarsolutions.santa.shared.domain.ClientSession;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Unit tests for LoginRequiredPagePresenter.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class LoginRequiredPagePresenterTest extends StubbedClientTestCase {

    /** Test onShowLoginRequiredPage(). */
    @Test public void testOnShowLoginRequiredPage() {
        ArgumentCaptor<LoginEventHandler> eventHandler = ArgumentCaptor.forClass(LoginEventHandler.class);
        IsWidget viewWidget = mock(IsWidget.class);

        LoginRequiredPagePresenter presenter = createPresenter();
        when(presenter.getView().getViewWidget()).thenReturn(viewWidget);
        presenter.onShowLoginRequiredPage("whatever");

        verify(presenter.getView()).setLoginEventHandler(eventHandler.capture());
        verify(presenter.getEventBus()).replaceModuleBody(viewWidget);
        assertSame(presenter, eventHandler.getValue().getParent());
        assertEquals("whatever", eventHandler.getValue().destinationUrl);
    }

    /** Test LoginEventHandler. */
    @Test public void testLoginEventHandler() {
        LoginRequiredPagePresenter presenter = createPresenter();
        LoginEventHandler eventHandler = new LoginEventHandler(presenter, "destinationUrl");
        assertSame(presenter, eventHandler.getParent());
        assertEquals("destinationUrl", eventHandler.destinationUrl);
        eventHandler.handleEvent(null);  // actual event doesn't matter
        verify(presenter.getEventBus()).showGoogleAccountsLoginPageForUrl("destinationUrl");
    }

    /** Create a properly-mocked presenter, including everything that needs to be injected. */
    private static LoginRequiredPagePresenter createPresenter() {
        ClientSession session = mock(ClientSession.class);
        SystemStateInjector systemStateInjector = mock(SystemStateInjector.class);
        when(systemStateInjector.getSession()).thenReturn(session);
        ExternalEventBus eventBus = mock(ExternalEventBus.class);
        ILoginRequiredPageView view = mock(ILoginRequiredPageView.class);

        LoginRequiredPagePresenter presenter = new LoginRequiredPagePresenter();
        presenter.setSystemStateInjector(systemStateInjector);
        presenter.setEventBus(eventBus);
        presenter.setView(view);

        assertSame(systemStateInjector, presenter.getSystemStateInjector());
        assertSame(session, presenter.getSession());
        assertSame(eventBus, presenter.getEventBus());
        assertSame(view, presenter.getView());

        return presenter;
    }
}
