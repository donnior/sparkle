package r;

class ClassloaderUtil {

    public static Class getCallingClass() {
        return CallerResolver.getCallerClass(2);
    }

    private static final class CallerResolver extends SecurityManager {
        private static final CallerResolver CALLER_RESOLVER = new CallerResolver();
        private static final int CALL_CONTEXT_OFFSET = 3; // may need to change if this class is redesigned
        protected Class[] getClassContext() {
            return super.getClassContext();
        }

        /*
        * Indexes into the current method call context with a given
        * offset.
        */
        private static Class getCallerClass(int callerOffset) {
            return CALLER_RESOLVER.getClassContext()[CALL_CONTEXT_OFFSET + callerOffset];
        }
        private static int getContextSize() {
            return CALLER_RESOLVER.getClassContext().length - CALL_CONTEXT_OFFSET;
        }
    }

}