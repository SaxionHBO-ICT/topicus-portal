package nl.saxion.jelmer.topitalk.model;

import java.util.ArrayList;

import nl.saxion.jelmer.topitalk.controller.ApiHandler;
import nl.saxion.jelmer.topitalk.controller.LoginHandler;

/**
 * Created by Nyds on 20/05/2016.
 */
public class TopiCoreModel {

    private User currentUser;
    private static TopiCoreModel topiCoreModel = null;
    private ArrayList<Post> postList;
    private ArrayList<User> users;

    private TopiCoreModel() {
        postList = new ArrayList<>();
        users = new ArrayList<>();

        //Dummy User
        User user = new User("Topicus", LoginHandler.encryptPassword("test"), "test", "test");
        users.add(user);
        generateDummyData();
    }

    public static TopiCoreModel getInstance() {

        if (topiCoreModel == null) {
            topiCoreModel = new TopiCoreModel();
        }
        return topiCoreModel;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<Post> getPostList() {
        return ApiHandler.getInstance().getPostList(); //Return the list from the database.
        //return postList;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void upvotePost(int postId) {

        if (!currentUser.hasUserUpvotedPost(postId)) {
            currentUser.addUserUpvotedPostId(postId);
            postList.get(postId).upvotePost();
        }
    }

    public void addPost(int authorId, String authorUsername, String title, String text) {
        //ApiHandler.getInstance().addPostToDb(new Post(authorId, authorUsername, title, text)); //Add a new post to the database.
        postList.add(new Post(authorId, authorUsername, title, text));
    }

    public void logoutCurrentUser() {
        currentUser = null;
    }

    private void generateDummyData() {

        Post post = new Post(users.get(0).getUserId(), users.get(0).getUsername(), "Over Topicus", "Topicus ontwikkelt nieuwe dienstverleningsconcepten waarbij de mogelijkheden van moderne technologie optimaal benut worden.");
        post.setPostScore(20);
        post.setHotTopic(true);
        postList.add(post);

        Post post1 = new Post(users.get(0).getUserId(), users.get(0).getUsername(), "Visie", "Topicus is werkzaam in de sectoren Finance, Onderwijs, Overheid en Zorg. Wij ontwikkelen nieuwe dienstverleningsconcepten voor onze klanten waarbij de mogelijkheden van moderne technologie optimaal benut worden.");
        post1.setPostScore(19);
        postList.add(post1);

        postList.add(new Post(users.get(0).getUserId(), users.get(0).getUsername(), "Ketenintegratie", "Topicus zorgt met kennis en techniek voor beter functionerende ketens ten behoeve van alle stakeholders in die keten. Topicus gaat uit van het idee dat instellingen en bedrijven niet op zichzelf staan, maar deel uitmaken van een keten. Het optimaliseren van de informatieuitwisseling binnen die keten, dat is waar we goed in zijn."));
        postList.add(new Post(users.get(0).getUserId(), users.get(0).getUsername(), "SaaS", "Topicus biedt het grootste deel van haar softwareoplossingen aan als Software as a Service (SaaS), ook wel bekend als Software on Demand. De door Topicus ontwikkelde software is webbased, benaderbaar via alle browsers en wordt aangeboden inclusief hosting, onderhoud en beheer."));
        postList.add(new Post(users.get(0).getUserId(), users.get(0).getUsername(), "Governance", "Topicus is qua omvang een serieuze speler op de ICT-markt geworden. Hierdoor is ook de noodzaak tot risicobeheersing - het garanderen van veiligheid en betrouwbaarheid van onze producten - steeds sterker geworden."));
        postList.add(new Post(users.get(0).getUserId(), users.get(0).getUsername(), "Kalender", "Topicus is te vinden op vakbeurzen, symposia, carrière-events en congressen. En we organiseren zo af en toe ook zelf een spraakmakend evenement."));
    }
}
