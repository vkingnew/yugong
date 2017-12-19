package com.taobao.yugong.translator;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.taobao.yugong.common.db.meta.ColumnMeta;
import com.taobao.yugong.common.db.meta.ColumnValue;
import com.taobao.yugong.common.model.record.Record;

import java.sql.Types;
import java.util.List;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClassLearningIsActiveReverseTranslator implements DataTranslator {


    public static final String IS_ACTIVE = "is_active";
    public static final String IS_DELETED = "is_deleted";

    @Override
    public String translatorSchema() {
        return null;
    }

    @Override
    public String translatorTable() {
        return null;
    }

    @Override
    public boolean translator(Record record) {
        return true;
    }

    @Override
    public List<Record> translator(List<Record> records) {
        records
                .stream()
                .forEach(
                        record -> {
                            ColumnValue isActive = record.getColumnByName(IS_ACTIVE);
                            ColumnValue isDeletedColumn = new ColumnValue();
                            ColumnMeta isDeletedMeta = new ColumnMeta(IS_DELETED, Types.BOOLEAN);
                            isDeletedColumn.setColumn(isDeletedMeta);
                            isDeletedColumn.setValue(!(Boolean) isActive.getValue());
                            record.addColumn(isDeletedColumn);
                            record.removeColumnByName(IS_ACTIVE);
                        });
        return records;
    }

}
