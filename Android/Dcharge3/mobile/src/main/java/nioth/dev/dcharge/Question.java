package nioth.dev.dcharge;

/**
 * Created by umeshkhanna on 2016-04-24.
 */
public class Question {
    String question;
    String answer;
    String time;
    int id_;

    public Question(String question,
                       String answer,
                       String time,
                       int id_)
    {
        this.question = question;
        this.answer = answer;
        this.time = time;
        this.id_ = id_;
    }


    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id_;
    }
}