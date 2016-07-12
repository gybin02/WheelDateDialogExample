/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seeker.datedialog.widget.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * <p/>
 * Author: wyouflf
 * Date: 13-7-24
 * Time: 下午12:23
 */
public class LogUtils {

    public static String customTagPrefix = "";
    public static String sTAG = "Log";

    private LogUtils() {
    }

    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static CustomLogger customLogger;

    public interface CustomLogger {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }

    public static void setLogEnable(boolean flag){
        LogUtils.allowD =flag;
        LogUtils.allowI =flag;
        LogUtils.allowV =flag;
        LogUtils.allowW =flag;
        LogUtils.allowWtf =flag;
        Config.getInstance().logFileSwitch(flag);
        Config.getInstance().logSwitch(flag);
    }

    @Deprecated
    public static void d(String content) {
        if (!allowD) return;
        Log.d(sTAG, checkEmpty(content));
    }

    private static String checkEmpty(String content) {
        return isEmpty(content) ? "null!!" : content;
    }

    public static void d(String content, Throwable tr) {
        if (!allowD) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, content, tr);
        } else {
            Log.d(tag, content, tr);
        }
    }

    @Deprecated
    public static void e(String content) {
        if (!allowE) return;
        Log.e(sTAG, checkEmpty(content));
    }

    public static void e(String content, Throwable tr) {
        if (!allowE) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, content, tr);
        } else {
            Log.e(tag, content, tr);
        }
    }

    @Deprecated
    public static void i(String content) {
        if (!allowI) return;
        Log.i(sTAG, checkEmpty(content));
    }

    public static void i(String content, Throwable tr) {
        if (!allowI) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.i(tag, content, tr);
        } else {
            Log.i(tag, content, tr);
        }
    }

    @Deprecated
    public static void v(String content) {
        if (!allowV) return;
        Log.v(sTAG, checkEmpty(content));
    }

    public static void v(String content, Throwable tr) {
        if (!allowV) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.v(tag, content, tr);
        } else {
            Log.v(tag, content, tr);
        }
    }

    @Deprecated
    public static void w(String content) {
        if (!allowW) return;Log.w(sTAG, checkEmpty(content));
    }

    public static void w(String content, Throwable tr) {
        if (!allowW) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, content, tr);
        } else {
            Log.w(tag, content, tr);
        }
    }

    @Deprecated
    public static void w(Throwable tr) {
        if (!allowWtf) return;Log.w(sTAG, tr);
    }


    public static void wtf(String content) {
        if (!allowWtf) return;Log.wtf(sTAG, checkEmpty(content));
    }

    public static void wtf(String content, Throwable tr) {
        if (!allowWtf) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, content, tr);
        } else {
            Log.wtf(tag, content, tr);
        }
    }

    @Deprecated
    public static void wtf(Throwable tr) {
        if (!allowWtf) return;
        Log.wtf(sTAG, tr);
    }


    public static class Config {

        private static final Config INSTANCE = new Config();

        private boolean isOpenSwitch = true;

        private boolean isLogFileExists = false;

        private boolean isOpenLogFileSwitch = true;

        private String logFilePath = null;

        private String includeTag = null;

        private int logLevel = Log.VERBOSE;

        private Config() {
        }

        public static Config getInstance() {
            return INSTANCE;
        }

        /**
         * 全局切换Log开关，是否打印log
         */
        public Config logSwitch(boolean isOpenSwitch) {
            this.isOpenSwitch = isOpenSwitch;
            return this;
        }

        /**
         * 设置默认标签，这种主要用于LogUtil.d() 这种默认一个大标签，通过子标签来区分内容的
         */
        public Config includeTag(String includeTag) {
            this.includeTag = includeTag;
            return this;
        }

        /**
         * 线上apk可以通过包路径或者sdcard路径下是否有某个文件来决定是否打开
         */
        public Config logFileSwitch(boolean isOpenLogFileSwitch) {
            this.isOpenLogFileSwitch = isOpenLogFileSwitch;
            return this;
        }

        /**
         * 控制log开关的file的path
         */
        public Config logFilePath(String logFilePath) {
            this.logFilePath = logFilePath;
            checkLogFilePath();
            return this;
        }

        public Config logLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

    }


    public static Config config() {
        return Config.getInstance();
    }


    /**
     * 检查文件是否存在
     */
    public static void checkLogFilePath() {
        boolean flag = false;
        try {
            File f = new File(Config.getInstance().logFilePath);
            flag = f.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Config.getInstance().isLogFileExists = flag;
    }

    /**
     * 是否打开log的总方法
     */
    private static boolean checkLogSwitch() {
        if (Config.getInstance().isOpenSwitch) {
            return true;
        } else if (!Config.getInstance().isOpenLogFileSwitch) {
            return false;
        } else if (!Config.getInstance().isLogFileExists) {
            return false;
        }
        return true;
    }

    /**
     * 打印 DEBUG 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg  消息内容，支持{@link String#format(String, Object...)}的语法
     * @param args format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void d(String tag, String msg, Object... args) {
        log(Log.DEBUG, null, tag, msg, args);
    }

    /**
     * 打印 VERBOSE 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg  消息内容，支持{@link String#format(String, Object...)}的语法
     * @param args format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void v(String tag, String msg, Object... args) {
        log(Log.VERBOSE, null, tag, msg, args);
    }

    /**
     * 打印 INFO 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg  消息内容，支持{@link String#format(String, Object...)}的语法
     * @param args format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void i(String tag, String msg, Object... args) {
        log(Log.INFO, null, tag, msg, args);
    }

    /**
     * 打印 ERROR 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg  消息内容，支持{@link String#format(String, Object...)}的语法
     * @param args format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void e(String tag, String msg, Object... args) {
        log(Log.ERROR, null, tag, msg, args);
    }

    /**
     * 打印 WARN 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg  消息内容，支持{@link String#format(String, Object...)}的语法
     * @param args format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void w(String tag, String msg, Object... args) {
        log(Log.WARN, null, tag, msg, args);
    }

    /**
     * 打印带有调用栈的 DEBUG 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg       消息内容，支持{@link String#format(String, Object...)}的语法
     * @param throwable
     * @param args      format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void d(String tag, String msg, Throwable throwable, Object... args) {
        log(Log.DEBUG, throwable, tag, msg, args);
    }

    /**
     * 打印带有调用栈的 VERBOSE 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg       消息内容，支持{@link String#format(String, Object...)}的语法
     * @param throwable
     * @param args      format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void v(String tag, String msg, Throwable throwable, Object... args) {
        log(Log.VERBOSE, throwable, tag, msg, args);
    }

    /**
     * 打印带有调用栈的 INFO 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg       消息内容，支持{@link String#format(String, Object...)}的语法
     * @param throwable
     * @param args      format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void i(String tag, String msg, Throwable throwable, Object... args) {
        log(Log.INFO, throwable, tag, msg, args);
    }

    /**
     * 打印带有调用栈的 ERROR 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg       消息内容，支持{@link String#format(String, Object...)}的语法
     * @param throwable
     * @param args      format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void e(String tag, String msg, Throwable throwable, Object... args) {
        log(Log.ERROR, throwable, tag, msg, args);
    }

    /**
     * 打印带有调用栈的 WARN 级别的日志，
     * 如果开启了debug模式
     * 则不过滤任何级别的日志
     *
     * @param tag
     * @param msg       消息内容，支持{@link String#format(String, Object...)}的语法
     * @param throwable
     * @param args      format msg的参数，可以为null
     * @throws java.util.IllegalFormatException if the format is invalid.
     */
    public static void w(String tag, String msg, Throwable throwable, Object... args) {
        log(Log.WARN, throwable, tag, msg, args);
    }

    private static void log(int level, Throwable throwable, String tag, String msg, Object... args) {
        if (!checkLogSwitch()) {
            return;
        }

        if (isBlank(Config.getInstance().includeTag)) {
            Config.getInstance().includeTag = "meiyou";
        }

        String includeTag = Config.getInstance().includeTag;
        boolean hasThrowable = (throwable != null);
        //msg content
        StringBuilder msgBuilder = new StringBuilder(includeTag);
        msgBuilder.append(" : ");
        if (isNotEmpty(msg) && args != null && args.length > 0) {
            msgBuilder.append(String.format(msg, args));
        } else {
            msgBuilder.append(msg);
        }

        switch (level) {
            case Log.VERBOSE:
                if (hasThrowable) {
                    Log.v(tag, msgBuilder.toString(), throwable);
                } else {
                    Log.v(tag, msgBuilder.toString());
                }
                break;
            case Log.DEBUG:
                if (hasThrowable) {
                    Log.d(tag, msgBuilder.toString(), throwable);
                } else {
                    Log.d(tag, msgBuilder.toString());
                }
                break;
            case Log.INFO:
                if (hasThrowable) {
                    Log.i(tag, msgBuilder.toString(), throwable);
                } else {
                    Log.i(tag, msgBuilder.toString());
                }
                break;
            case Log.WARN:
                if (hasThrowable) {
                    Log.w(tag, msgBuilder.toString(), throwable);
                } else {
                    Log.w(tag, msgBuilder.toString());
                }
                break;
            case Log.ERROR:
                if (hasThrowable) {
                    Log.e(tag, msgBuilder.toString(), throwable);
                } else {
                    Log.e(tag, msgBuilder.toString());
                }
                break;
        }
    }


    // LogWrite
    private static String LOG_PATH_SDCARD_DIR;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
    private static Process process;

    public static void startLogcatLog() {
        init();
        createLog();
    }

    public static void stopLogcatLog() {
        if (process != null) {
            process.destroy();
        }
    }

    private static void init() {
        LOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MeetyouLog";
        createLogDir();
    }

    /**
     * write the log
     */
    public static void createLog() {
        // TODOWriteLog
        List<String> commandList = new ArrayList<String>();
        commandList.add("logcat");
        commandList.add("-f");
        commandList.add(getLogPath());
        commandList.add("-v");
        commandList.add("time");
        try {
            process = Runtime.getRuntime().exec(
                    commandList.toArray(new String[commandList.size()]));
        } catch (Exception e) {
            e.printStackTrace();
            //Log.e(TAG,e.getMessage(), e);
        }
    }

    /**
     * the path of the log file
     *
     * @return
     */
    public static String getLogPath() {
        createLogDir();
        String logFileName = "meetyou_logcat_" + simpleDateFormat.format(new Date()) + ".log";// name
        return LOG_PATH_SDCARD_DIR + File.separator + logFileName;

    }

    /**
     * make the dir
     */
    private static void createLogDir() {
        File file;
        boolean mkOk;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            file = new File(LOG_PATH_SDCARD_DIR);
            if (!file.isDirectory()) {
                mkOk = file.mkdirs();
                if (!mkOk) {
                    return;
                }
            }
        }
    }

    public void writeLog(String str) {

    }

/************************************************/
    public static String getSubString(String str, int start, int end) {
        return new String(str.substring(start, end));
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }


    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNull(String str) {
        try {
            return str == null?true:(str != null?(!str.equals("") && !str.equals("null") && !str.equals("[]")?str.trim().equals("") || str.trim().equals("null"):true):false);
        } catch (Exception var2) {
            var2.printStackTrace();
            return true;
        }
    }

    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
