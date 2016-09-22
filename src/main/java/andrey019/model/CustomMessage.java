package andrey019.model;


public class CustomMessage {
    private String to;
    private String from;
    private String subject;
    private String text;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + to.hashCode();
        result = prime * result + text.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CustomMessage))
            return false;
        CustomMessage other = (CustomMessage) obj;
        if (!to.equals(other.to))
            return false;
        if (text == null) {
            if (other.getText() != null)
                return false;
        } else if (!text.equals(other.getText()))
            return false;
        return true;
    }
}
