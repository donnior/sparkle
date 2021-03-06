package org.agilej.sparkle.core.view;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.mvc.ViewRender;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewManagerTest {

    @Test
    public void test_build_in_view_renders(){
        ViewRenderRegistration viewRenderManager = new ViewRenderRegistration();
        List<ViewRender> viewRenders = viewRenderManager.getAllOrderedViewRenders();
        assertTrue(4 == viewRenders.size());
        assertEquals(JSONTextViewRender.class, viewRenders.get(0).getClass());
        assertEquals(JSONModelViewRender.class, viewRenders.get(1).getClass());
        assertEquals(JSONViewRender.class, viewRenders.get(2).getClass());
        assertEquals(TextViewRender.class, viewRenders.get(3).getClass());
    }


    @Test
    public void test_register_app_scoped_view_renders(){
        ViewRenderRegistration viewRenderManager = new ViewRenderRegistration();

        List<Class<? extends ViewRender>> viewRenderClasses = new ArrayList<>();
        viewRenderClasses.add(TestAppScopedViewRender.class);
        viewRenderManager.registerAppScopedViewRender(viewRenderClasses);

        List<ViewRender> viewRenders = viewRenderManager.getAllOrderedViewRenders();

        assertTrue(5 == viewRenders.size());
        assertEquals(TestAppScopedViewRender.class, viewRenders.get(0).getClass());
    }

    @Test
    public void test_register_vendor_view_renders(){
        ViewRenderRegistration viewRenderManager = new ViewRenderRegistration();

        List<ViewRender> viewRenderClasses = new ArrayList<>();
        viewRenderClasses.add(new TestAppScopedViewRender());
        viewRenderManager.registerVendorViewRenders(viewRenderClasses);

        List<ViewRender> viewRenders = viewRenderManager.getAllOrderedViewRenders();

        assertTrue(5 == viewRenders.size());
        assertEquals(TestAppScopedViewRender.class, viewRenders.get(4).getClass());
    }

    public static class TestAppScopedViewRender implements ViewRender{

        @Override
        public boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult) {
            return false;
        }

        @Override
        public void renderView(Object result, Object controller, WebRequest request) throws IOException {
        }
    }
}
