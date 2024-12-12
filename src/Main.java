import java.util.ArrayList;
import java.util.List;

interface Mediator
{
    void sendMessage(String message, User user);
    void addUser(User user);
}

class ChatMediator implements Mediator
{
    private List<User> users = new ArrayList<>();

    public void addUser(User user)
    {
        users.add(user);
    }

    public void sendMessage(String message, User sender)
    {
        for (User user : users)
        {
            if (user != sender) user.receive(message);
        }
    }
}

abstract class User
{
    protected Mediator mediator;
    protected String name;

    public User(Mediator mediator, String name)
    {
        this.mediator = mediator;
        this.name = name;
    }

    public abstract void send(String message);
    public abstract void receive(String message);
}

class CurrentUser extends User
{
    public CurrentUser(Mediator mediator, String name)
    {
        super(mediator, name);
    }

    public void send(String message)
    {
        System.out.println(name + " отправляет сообщение: " + message);
        mediator.sendMessage(message, this);
    }

    public void receive(String message)
    {
        System.out.println(name + " получает сообщение: " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        ChatMediator mediator = new ChatMediator();

        User user1 = new CurrentUser(mediator, "Иван");
        User user2 = new CurrentUser(mediator, "Петя");
        User user3 = new CurrentUser(mediator, "Саня");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);

        user1.send("Привет, всем!");
        user2.send("Привет, Иван!");
    }
}