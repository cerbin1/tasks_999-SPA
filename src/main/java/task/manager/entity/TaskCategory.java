package task.manager.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = MyEnumDeserializer.class)

public enum TaskCategory {
    NO_CATEGORY, ENGINEERING, SALES, DOCUMENTATION, WEB_DESIGN, TESTING
}
