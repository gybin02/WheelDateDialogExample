# WheelDateDialogExample
WheelDateDialogExample
### Target
 Delicate Wheel Date Dialog. 
 Feature：
 1. 灵活的Wheel实现，扩展至Gallery，可以自定义 Wheel的Item
 2. 日期支持限定开始时间，结束时间，默认时间
 3. 支持日期上显示，今天，昨天，前天等提示
 4. 使用自底向上弹出对话框

### How to use
```java
 Calendar calendarToday = Calendar.getInstance();
            Calendar calendarStart = (Calendar) calendarToday.clone();
            calendarStart.add(Calendar.DAY_OF_YEAR,-365);
            Calendar calendarEnd = (Calendar) calendarToday.clone();
            calendarEnd.add(Calendar.DAY_OF_YEAR,365);
            
            dateDialog = new HomeDateDialogBuilder().setContext(this)
                    .setTitle("选择日期")
                    .setStartCalendar(calendarStart)
                    .setEndCalendar(calendarEnd)
                    .setSelectCalendar(calendarToday)
                    .setState(HomeDateDialog.STATE_TODAY).setOnDialogResult(new HomeDateDialog.OnDialogResult() {
                        @Override
                        public void onSelectedResult(boolean bOk, Calendar calendar) {
                            if (bOk) {
                                //TODO
                            }
                        }

                        @Override
                        public void onScrollFinish(Calendar calendar) {

                        }
                    }).createHomeDateDialog();
            dateDialog.show();
```

### Screenshot
![screenshot](https://raw.githubusercontent.com/gybin02/WheelDateDialogExample/master/QQ%E6%88%AA%E5%9B%BE20160712180949.png)

