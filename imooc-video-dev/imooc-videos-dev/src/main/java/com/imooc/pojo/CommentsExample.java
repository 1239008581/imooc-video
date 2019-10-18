package com.imooc.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommentsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idIsNull() {
            addCriterion("father_comment_id is null");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idIsNotNull() {
            addCriterion("father_comment_id is not null");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idEqualTo(String value) {
            addCriterion("father_comment_id =", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idNotEqualTo(String value) {
            addCriterion("father_comment_id <>", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idGreaterThan(String value) {
            addCriterion("father_comment_id >", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idGreaterThanOrEqualTo(String value) {
            addCriterion("father_comment_id >=", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idLessThan(String value) {
            addCriterion("father_comment_id <", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idLessThanOrEqualTo(String value) {
            addCriterion("father_comment_id <=", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idLike(String value) {
            addCriterion("father_comment_id like", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idNotLike(String value) {
            addCriterion("father_comment_id not like", value, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idIn(List<String> values) {
            addCriterion("father_comment_id in", values, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idNotIn(List<String> values) {
            addCriterion("father_comment_id not in", values, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idBetween(String value1, String value2) {
            addCriterion("father_comment_id between", value1, value2, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andFather_comment_idNotBetween(String value1, String value2) {
            addCriterion("father_comment_id not between", value1, value2, "father_comment_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idIsNull() {
            addCriterion("to_user_id is null");
            return (Criteria) this;
        }

        public Criteria andTo_user_idIsNotNull() {
            addCriterion("to_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andTo_user_idEqualTo(String value) {
            addCriterion("to_user_id =", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idNotEqualTo(String value) {
            addCriterion("to_user_id <>", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idGreaterThan(String value) {
            addCriterion("to_user_id >", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idGreaterThanOrEqualTo(String value) {
            addCriterion("to_user_id >=", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idLessThan(String value) {
            addCriterion("to_user_id <", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idLessThanOrEqualTo(String value) {
            addCriterion("to_user_id <=", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idLike(String value) {
            addCriterion("to_user_id like", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idNotLike(String value) {
            addCriterion("to_user_id not like", value, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idIn(List<String> values) {
            addCriterion("to_user_id in", values, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idNotIn(List<String> values) {
            addCriterion("to_user_id not in", values, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idBetween(String value1, String value2) {
            addCriterion("to_user_id between", value1, value2, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andTo_user_idNotBetween(String value1, String value2) {
            addCriterion("to_user_id not between", value1, value2, "to_user_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idIsNull() {
            addCriterion("video_id is null");
            return (Criteria) this;
        }

        public Criteria andVideo_idIsNotNull() {
            addCriterion("video_id is not null");
            return (Criteria) this;
        }

        public Criteria andVideo_idEqualTo(String value) {
            addCriterion("video_id =", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idNotEqualTo(String value) {
            addCriterion("video_id <>", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idGreaterThan(String value) {
            addCriterion("video_id >", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idGreaterThanOrEqualTo(String value) {
            addCriterion("video_id >=", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idLessThan(String value) {
            addCriterion("video_id <", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idLessThanOrEqualTo(String value) {
            addCriterion("video_id <=", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idLike(String value) {
            addCriterion("video_id like", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idNotLike(String value) {
            addCriterion("video_id not like", value, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idIn(List<String> values) {
            addCriterion("video_id in", values, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idNotIn(List<String> values) {
            addCriterion("video_id not in", values, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idBetween(String value1, String value2) {
            addCriterion("video_id between", value1, value2, "video_id");
            return (Criteria) this;
        }

        public Criteria andVideo_idNotBetween(String value1, String value2) {
            addCriterion("video_id not between", value1, value2, "video_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idIsNull() {
            addCriterion("from_user_id is null");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idIsNotNull() {
            addCriterion("from_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idEqualTo(String value) {
            addCriterion("from_user_id =", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idNotEqualTo(String value) {
            addCriterion("from_user_id <>", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idGreaterThan(String value) {
            addCriterion("from_user_id >", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idGreaterThanOrEqualTo(String value) {
            addCriterion("from_user_id >=", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idLessThan(String value) {
            addCriterion("from_user_id <", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idLessThanOrEqualTo(String value) {
            addCriterion("from_user_id <=", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idLike(String value) {
            addCriterion("from_user_id like", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idNotLike(String value) {
            addCriterion("from_user_id not like", value, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idIn(List<String> values) {
            addCriterion("from_user_id in", values, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idNotIn(List<String> values) {
            addCriterion("from_user_id not in", values, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idBetween(String value1, String value2) {
            addCriterion("from_user_id between", value1, value2, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andFrom_user_idNotBetween(String value1, String value2) {
            addCriterion("from_user_id not between", value1, value2, "from_user_id");
            return (Criteria) this;
        }

        public Criteria andCreate_timeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreate_timeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_timeEqualTo(Date value) {
            addCriterion("create_time =", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeGreaterThan(Date value) {
            addCriterion("create_time >", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeLessThan(Date value) {
            addCriterion("create_time <", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeIn(List<Date> values) {
            addCriterion("create_time in", values, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "create_time");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}