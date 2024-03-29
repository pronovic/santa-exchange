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
package com.cedarsolutions.santa.client.common.widget;

import com.cedarsolutions.client.gwt.widget.AbstractWidgetUtils;
import com.cedarsolutions.santa.client.SantaExchangeConfig;
import com.cedarsolutions.shared.domain.ErrorDescription;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Widget-related utilities.
 *
 * <p>
 * Unlike a lot of other utility classes, which typically provide static
 * methods, this class is a singleton.
 * </p>
 *
 * <p>
 * It's difficult to mock static method calls, but I really want that option
 * for some of these methods.  Normally, I would fall back on dependency
 * injection to solve this problem.  However, it's not always possible to
 * inject an instance of this class into all of the places where it needs to be
 * used.  Making the class a singleton works around that problem.
 * </p>
 *
 * <p>
 * For testing, the stubbed client test framework is wired into the
 * GWT.create() call within getInstance().  The framework "automagically"
 * generates a mocked version of this class for use by unit tests.  However,
 * keep in mind that even the mock object is a singleton, so you must remember
 * to reset the mock between test cases.
 * </p>
 *
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class WidgetUtils extends AbstractWidgetUtils {

    /** Id of the AJAX progress indicator on the main page. */
    private static final String AJAX_PROGRESS_INDICATOR = "ajaxProgressIndicator";

    /** Id of the AJAX progress indicator text span on the main page. */
    private static final String AJAX_PROGRESS_INDICATOR_TEXT = "ajaxProgressIndicatorText";

    /** Singleton instance. */
    private static WidgetUtils INSTANCE;

    /** Default constructor is private so class cannot be instantiated. */
    private WidgetUtils() {
    }

    /** Get an instance of this class to use. */
    public static synchronized WidgetUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = GWT.create(WidgetUtils.class);
        }

        return INSTANCE;
    }

    /** Get the current application version. */
    public String getApplicationVersion() {
        SantaExchangeConfig config = GWT.create(SantaExchangeConfig.class);
        String name = config.system_applicationName();
        String version = config.system_versionNumber();
        String date = config.system_releaseDate();
        return name + " v" + version + " (" + date + ") using HRD";
    }

    /**
     * Show an error via the error pop-up, for situations when the event bus is not
     * available. <i>Most callers should use the event bus showErrorPopup() method
     * instead of this method.</i>
     */
    public void showErrorPopup(ErrorDescription error) {
        new ErrorPopup().showError(error);
    }

    /** Set the progress indicator text. */
    protected void setProgressIndicatorText(String text) {
        RootPanel.get(AJAX_PROGRESS_INDICATOR_TEXT).getElement().setInnerText(text);
    }

    /** Hide the progress indicator. */
    public void hideProgressIndicator() {
        RootPanel.get(AJAX_PROGRESS_INDICATOR).setVisible(false);
        this.setProgressIndicatorText("");
    }

    /** Show the progress indicator with specific text. */
    public void showProgressIndicator(String text) {
        this.setProgressIndicatorText(text);
        RootPanel.get(AJAX_PROGRESS_INDICATOR).setVisible(true);
    }

    /** Show the "loading" progress indicator. */
    public void showLoadingProgressIndicator() {
        WidgetConstants constants = GWT.create(WidgetConstants.class);
        this.showProgressIndicator(constants.progressIndicator_loading());
    }

    /** Show the "please wait" progress indicator. */
    public void showPleaseWaitProgressIndicator() {
        WidgetConstants constants = GWT.create(WidgetConstants.class);
        this.showProgressIndicator(constants.progressIndicator_pleaseWait());
    }

}
