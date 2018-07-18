package at.linguathek.spelling.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task  implements Serializable {
    String description;
    List<Statement> statements;

    public Task() {
        statements = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
}