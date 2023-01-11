package ru.zzbo.concretemobile.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ru.zzbo.concretemobile.R;

public class TableView extends HorizontalScrollView {
    private static final String TAG = TableView.class.getSimpleName();
    private OnTableViewListener tableViewListener;
    private TableLayout tableLayout;
    private final Context context;
    private int countRows;

    private String defaultEmptyCell;
    private int column;
    private int itemPreviewCount;
    private int cellTextGravity, headerTextGravity;
    private int cellBackground, headerBackground;
    private int cellTextColor, headerTextColor;
    private int cellSelectedColor;
    private int cellPadding, headerPadding;
    private boolean isStretchColumn;

    public interface OnTableViewListener {
        void onRowClicked(@NonNull TableRow tableRow, int position, List<String> row);

        void onLongClicked(@NonNull TableRow tableRow, int position, List<String> row);
    }

    public TableView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TableView, 0, 0);
            try {
                defaultEmptyCell = a.getString(R.styleable.TableView_defaultEmptyCell);
                defaultEmptyCell = defaultEmptyCell == null ? "" : defaultEmptyCell;
                cellPadding = a.getDimensionPixelSize(R.styleable.TableView_cellPadding, 10);
                headerPadding = a.getDimensionPixelSize(R.styleable.TableView_headerPadding, 10);
                isStretchColumn = a.getBoolean(R.styleable.TableView_isStretch, false);
                cellTextGravity = a.getInt(R.styleable.TableView_cellTextGravity, Gravity.START);
                cellSelectedColor = a.getColor(R.styleable.TableView_cellSelectedColor, Color.WHITE);
                cellBackground = a.getColor(R.styleable.TableView_cellBackground, Color.WHITE);
                cellTextColor = a.getColor(R.styleable.TableView_cellTextColor, Color.BLACK);
                headerBackground = a.getColor(R.styleable.TableView_headerBackground, Color.WHITE);
                headerTextColor = a.getColor(R.styleable.TableView_headerTextColor, Color.BLACK);
                headerTextGravity = a.getInt(R.styleable.TableView_headerTextGravity, Gravity.START);
                itemPreviewCount = a.getInt(R.styleable.TableView_itemPreviewCount, 10);
            } finally {
                a.recycle();
            }
        }
        initView();
    }

    /**
     * Initiate views
     */
    private void initView() {
        // init table layout
        tableLayout = new TableLayout(context);
        tableLayout.setStretchAllColumns(isStretchColumn);
        addView(tableLayout);

        setCellTextGravity(cellTextGravity);
        setHeaderTextGravity(headerTextGravity);

        if (isInEditMode()) {

            setCellTextGravity(cellTextGravity);
            setHeaderTextGravity(headerTextGravity);

            defaultEmptyCell = TextUtils.isEmpty(defaultEmptyCell) ? "" : defaultEmptyCell;
            cellBackground = cellBackground == 0 ? Color.WHITE : cellBackground;
            cellTextColor = cellTextColor == 0 ? Color.BLACK : cellTextColor;
            headerBackground = headerBackground == 0 ? Color.WHITE : headerBackground;
            headerTextColor = headerTextColor == 0 ? Color.BLACK : headerTextColor;

            // Add Header
            String value = "id | column2 | column3 | column4 | column5 | column6";
            List<String> list = Arrays.asList(value.split("\\|"));
            addHeader(list);

            // Add Cells
            LinkedHashMap<Integer, List<String>> rows = new LinkedHashMap<>();
            for (int i = 0; i < itemPreviewCount; i++) {
                value = (i + 1) + "| text2 | text3 | text4 | text5 | text6 | ";
                rows.put(i, Arrays.asList(value.split("\\|")));
            }
            addRows(rows);
        }
    }

    /**
     * TableView action listener
     */
    @SuppressWarnings("unused")
    public void setTableViewListener(OnTableViewListener listener) {
        tableViewListener = listener;
    }

    /**
     * Set stretch cell
     *
     * @param isStretchColumn is stretch cell (true/false)
     */
    @SuppressWarnings("unused")
    public void setStretchColumn(boolean isStretchColumn) {
        this.isStretchColumn = isStretchColumn;
        invalidate();
    }

    /**
     * Set text gravity
     *
     * @param gravity gravity(start, center, end)
     */
    @SuppressWarnings("unused")
    private void setCellTextGravity(int gravity) {
        cellTextGravity = getGravity(gravity);
    }

    /**
     * Set text gravity
     *
     * @param gravity gravity(start, center, end)
     */
    @SuppressWarnings("unused")
    private void setHeaderTextGravity(int gravity) {
        headerTextGravity = getGravity(gravity);
    }

    /**
     * Get Gravity
     */
    private int getGravity(int gravity) {
        switch (gravity) {
            case 0x00800005:
                return Gravity.END;

            case 0x11:
                return Gravity.CENTER;

            default:
                return Gravity.START; // 0x00800003
        }
    }

    /**
     * Add header
     *
     * @param header list of table header
     */
    public void addHeader(List<String> header) {
        if (header == null || header.isEmpty()) return;

        column = header.size();

        TableRow tableRow = new TableRow(context);
        //Table Layout parameters
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-1, -2, 1.0f);
        tableRow.setLayoutParams(layoutParams);
        tableRow.setBackgroundColor(headerBackground);

        for (int i = 0; i < header.size(); i++) {
            if (i >= column) break;
            String val = getDefaultEmptyCell(header.get(i));
            tableRow.addView(getHeaderCell(val));
        }

        if (tableLayout.getChildAt(0) != null) {
            tableLayout.removeView(tableLayout.getChildAt(0));
        }
        tableRow.setTag("header_row");
        tableLayout.addView(tableRow, 0);
    }

    public void clear() {
        for (int i = 0; i < tableLayout.getChildCount(); i = 0) {
            View child = tableLayout.getChildAt(0);
            if (child != null && child instanceof TableRow) {
                TableRow row = (TableRow) child;
                row.removeAllViews();
                tableLayout.removeViewAt(i);
            }
        }
        countRows = 0;
    }

    /**
     * Add single row
     */
    public void addRow(List<String> row) {
        if (column != 0) {
            if (row == null || row.isEmpty()) return;

            TableRow tableRow = new TableRow(context);
            tableRow.setClickable(true);
            tableRow.setFocusable(true);
            tableRow.setBackground(cellBackgroundSelector(cellSelectedColor));

            List<String> copyRow = new LinkedList<>();

            for (int i = 0; i < row.size(); i++) {
                if (i >= column) break;
                String val = getDefaultEmptyCell(row.get(i));
                tableRow.addView(getCell(val));
                if (TextUtils.isEmpty(defaultEmptyCell)) {

                }
                if (!TextUtils.isEmpty(defaultEmptyCell) && " ".equalsIgnoreCase(row.get(i))) {
                    row.set(i, defaultEmptyCell);
                }
                copyRow.add(row.get(i));
            }

            countRows++;

            // on clicked
            tableRow.setOnClickListener(v -> {
                if (tableViewListener != null)
                    tableViewListener.onRowClicked(tableRow, countRows, copyRow);
            });

            // on long clicked
            tableRow.setOnLongClickListener(v -> {
                if (tableViewListener != null)
                    tableViewListener.onLongClicked(tableRow, countRows, copyRow);
                return true;
            });

            tableLayout.addView(tableRow, countRows);

        } else {
            Log.i(TAG, "addRow: Please specify column amount before adding rows");
        }
    }

    /**
     * Add rows
     */
    public void addRows(LinkedHashMap<Integer, List<String>> rows) {
        if (rows == null || rows.isEmpty()) return;

        for (Map.Entry<Integer, List<String>> item : rows.entrySet()) {
            addRow(item.getValue());
        }
    }

    /**
     * Create cell
     */
    @NonNull
    private TextView getHeaderCell(String cell) {
        if (TextUtils.isEmpty(cell)) cell = getDefaultEmptyCell(cell);
        TextView textView = new TextView(context);
        textView.setText(cell);

        if (defaultEmptyCell.equals(cell)) {
            textView.setGravity(Gravity.CENTER);
        } else {
            textView.setGravity(headerTextGravity);
        }

        int padding = headerPadding == 0 ? cellPadding : headerPadding;
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextColor(headerTextColor);

        return textView;
    }

    /**
     * Create cell
     */
    @NonNull
    private TextView getCell(String cell) {
        if (TextUtils.isEmpty(cell)) cell = getDefaultEmptyCell(cell);
        TextView textView = new TextView(context);
        textView.setText(cell);

        if (defaultEmptyCell.equals(cell)) {
            textView.setGravity(Gravity.CENTER);
        } else {
            textView.setGravity(headerTextGravity);
        }

        textView.setPadding(cellPadding, cellPadding, cellPadding, cellPadding);
        textView.setTextColor(cellTextColor);

        return textView;
    }

    /**
     * Set table row background selector
     *
     * @param selected Selected state color
     * @return StateListDrawable
     */
    private StateListDrawable cellBackgroundSelector(@ColorInt int selected) {
        StateListDrawable res = new StateListDrawable();
        res.setExitFadeDuration(200);
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(selected));
        res.addState(new int[]{android.R.attr.stateNotNeeded}, new ColorDrawable(cellBackground == 0 ? Color.TRANSPARENT : cellBackground));
        return res;
    }

    /**
     * Получение значения ячейки по умолчанию, когда ячейка является нулевой или пустой
     *
     * @return значение строки ячейки по умолчанию
     */
    private String getDefaultEmptyCell(String val) {
        return TextUtils.isEmpty(val) || val.equals(" ") ? defaultEmptyCell : val;
    }

    @NonNull
    @SuppressWarnings("unused")
    private String getSafeString(String value) {
        return value == null ? "" : value;
    }

    @SuppressWarnings("unused")
    private int dpToPx(int dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp);
    }

}
