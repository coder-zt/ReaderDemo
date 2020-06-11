package com.example.readerdemo.Reader.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 书本信息
 */
public class BookBean {
    /**
     *<html>
     *  <head></head>
     *  <body>
     *   <h2>CHAPTER I</h2>
     *   <p style="text-align: center"><b>HOW IT STARTED</b></p>
     *   <p>The Adventure Club had its inception, one evening toward the last of June, in Number 17 Sumner Hall, which is the oldest, most vine-hidden and most hallowed of the seven dormitories of Dexter Academy. It was a particularly warm evening, the two windows were wide open and the green-shaded light on the study table in the centre of the room had been turned low—Sumner prided itself on being conservative to the extent of gas instead of electricity and tin bathtubs instead of porcelain—and in the dim radiance the three occupants of the room were scarcely more than darker blurs.</p>
     *   <p>Since final examinations had ended that afternoon and Graduation Day was only some twenty-eight hours away, none of the three was doing anything more onerous than yawning, and the yawn which came from Perry Bush, didn't sound as though it cost much of an effort. It was, rather, a comfortable, sleepy yawn, one that expressed contentment and relief, a sort of &quot;Glad-that's-over-and-I'm-still-alive&quot; yawn.</p>
     *   <p>There was a window-seat under each casement in Number 17, and each was occupied by a recumbent figure. Perry was on the right-hand seat, his hands under his head and one foot sprawled on the floor, and Joe Ingersoll was in the other, his slim, white-trousered legs jack-knifed against the darker square of the open window. Near Joe, his feet tucked sociably against Joe's ribs, Steve Chapman, the third of the trio, reclined in a Morris chair. I use the word reclined advisedly, for Steve had lowered the back of the chair to its last notch, and to say that he was sitting would require a stretch of the imagination almost as long as Steve himself! Through the windows Steve could see the dark masses of the campus elms, an occasional star between the branches, and, by raising his head the fraction of an inch, the lights in the upper story of Hawthorne, across the yard. Somewhere under the trees outside a group of fellows were singing to the accompaniment of a wailing ukelele. They sang softly, so that the words floated gently up to the open casements just distinguishable:</p>
     *   <p>&quot;Aw, shut up!&quot; muttered Perry, breaking the silence that had held them for several minutes. Joe Ingersoll laughed softly.</p>
     *   <p>&quot;You don't seem to like the efforts of the—um—sweet-voiced choristers,&quot; he said in his slow way.</p>
     *   <p>&quot;I don't like the sob-stuff,&quot; replied Perry resentfully. &quot;What's the use of rubbing it in? Why not let a fellow be cheerful after he has got through by the skin of his teeth and kicked his books under the bed? Gosh, some folks never want anyone to be happy!&quot; He raised himself by painful effort and peered out and down into the gloom. &quot;Sophs, I'll bet,&quot; he murmured, falling back again on the cushions. &quot;No one else would sit out here on the grass and sing school songs two days before the end. I hope that idiot singing second bass will get a brown-tail caterpillar down his neck!&quot;</p>
     *   <p>&quot;The end!&quot; observed Steve Chapman. &quot;You say that as if we were all going to die the day after tomorrow, Perry! Cheer up! Vacation's coming!&quot;</p>
     *   <p>&quot;Vacation be blowed!&quot; responded Perry. &quot;What's that amount to, anyway? Nothing ever happens to me in vacation. It's all well enough for you fellows to laugh. You're going up to college together in the Fall. I'm coming back to this rotten hole all alone!&quot;</p>
     *   <p>&quot;Not quite alone, Sweet Youth,&quot; corrected Joe. &quot;There will be some four hundred other fellows here.&quot;</p>
     *   <p>&quot;Oh, well, you know what I mean,&quot; said Perry impatiently. &quot;You and Steve will be gone, and I don't give a hang for any other chaps!&quot;</p>
     *   <p>He ended somewhat defiantly, conscious that he had indulged in a most unmanly display of sentiment, and was glad that the darkness hid the confusion and heightened colour that followed the confession. Steve and Joe charitably pretended not to have noticed the lamentable exhibition of feeling, and a silence followed, during which the voices of the singers once more became audible.</p>
     *   <p>&quot;<i>Cut it out!</i>&quot; Perry leaned over the windowsill and bawled the command down into the darkness. A defiant jeer answered him.</p>
     *   <p>&quot;Don't be fresh,&quot; said Steve reprovingly. Perry mumbled and relapsed into silence. Presently, sighing as he changed his position, Joe said:</p>
     *   <p>&quot;I believe Perry's right about vacation, Steve. Nothing much ever does happen to a fellow in Summer. I believe I've had more fun in school than at home the last six years.&quot;</p>
     *   <p>The others considered the statement a minute. Then: &quot;Correct,&quot; said Steve. &quot;It's so, I guess. We're always crazy to get home in June and just as crazy to get back to school again in September, and I believe we all have more good times here than at home.&quot;</p>
     *   <p>&quot;Of course we do,&quot; agreed Perry animatedly. &quot;Anyway, I do. Summers are all just the same. My folks lug me off to the Water Gap and we stay there until it's time to come back here. I play tennis and go motoring and sit around on the porch and—and—bathe—&quot;</p>
     *   <p>&quot;Let's hope so,&quot; interpolated Joe gravely.</p>
     *   <p>&quot;And nothing really interesting ever happens,&quot; ended Perry despairingly. &quot;Gee, I'd like to be a pirate or—or something!&quot;</p>
     *   <p>&quot;Summers <i>are</i> rather deadly,&quot; assented Steve. &quot;We go to the seashore, but the place is filled with swells, and about all they do is change their clothes, eat and sleep. When you get ready for piracy, Perry, let me know, will you! I'd like to sign-on.&quot;</p>
     *   <p>&quot;Put me down, too,&quot; said Joe. &quot;I've always had a—um—sneaking idea that I'd make a bully pirate. I'm naturally bloodthirsty and cruel. And I've got a mental list of folks who—um—I'd like to watch walk the plank!&quot;</p>
     *   <p>&quot;Fellows of our ages have a rotten time of it, anyway,&quot; Perry grumbled. &quot;We're too old to play kids' games and too young to do anything worth while. What I'd like to do—&quot;</p>
     *   <p>&quot;Proceed, Sweet Youth,&quot; Joe prompted after a moment.</p>
     *   <p>&quot;Well, I'd like to—to start something! I'd like to get away somewhere and do things. I'm tired of loafing around in white flannels all day and keeping my hands clean. And I'm tired of dabbing whitewash on my shoes! Didn't you fellows ever think that you'd like to get good and dirty and not have to care? Wouldn't you like to put on an old flannel shirt and a pair of khaki trousers and some 'sneakers' and—and roll in the mud?&quot;</p>
     *   <p>&quot;Elemental stuff,&quot; murmured Joe. &quot;He's been reading Jack London.&quot;</p>
     *   <p>&quot;Well, that's the way I feel, lots of times,&quot; said Perry defiantly. &quot;I'm tired of being clean and white, and I'm tired of dinner jackets, and I'm sick to death of hotel porches! Gee, a healthy chap never was intended to lead the life of a white poodle with a pink ribbon around his neck! Me for some rough-stuff!&quot;</p>
     *   <p>&quot;You're dead right, too,&quot; agreed Steve. &quot;That kind of thing is all right for Joe, of course. Joe's a natural-born 'fusser.' He's never happier than when he's dolled up in a sport-shirt and a lavender scarf and toasting marshmallows. But—&quot;</p>
     *   <p>&quot;Is that so?&quot; inquired Joe with deep sarcasm. &quot;If I was half the 'fusser' you are—&quot;</p>
     *   <p>&quot;What I want,&quot; interrupted Perry, warming to his theme, &quot;is adventure! I'd like to hunt big game, or discover the North Pole—&quot;</p>
     *   <p>&quot;You're a year or two late,&quot; murmured Joe.</p>
     *   <p>&quot;—or dig for hidden treasure!&quot;</p>
     *   <p>&quot;You should—um—change your course of reading,&quot; advised Joe. &quot;Too much Roosevelt and Peary and Stevenson is your trouble. Read the classics for awhile—or the Patty Books.&quot;</p>
     *   <p>&quot;That's all right, but you chaps are just the same, only you won't own up to it.&quot;</p>
     *   <p>&quot;One of us will,&quot; said Steve; &quot;and does.&quot;</p>
     *   <p>&quot;Make it two,&quot; yawned Joe. &quot;Beneath this—um—this polished exterior there beats a heart—I mean there flows the red blood of—&quot;</p>
     *   <p>&quot;Look here, fellows, why not?&quot; asked Steve.</p>
     *   <p>&quot;Why not what?&quot; asked Perry.</p>
     *   <p>&quot;Why not have adventures? They say that all you have to do is look for them.&quot;</p>
     *   <p>&quot;Don't you believe it! I've looked for them for years and I've never seen one yet.&quot; Perry swung his feet to the floor and sat up.</p>
     *   <p>&quot;Well, not at Delaware Water Gap, naturally. You've got to move around, son. You don't find them by sitting all day with your feet on the rail of a hotel piazza.&quot;</p>
     *   <p>&quot;Where do you find them, then?&quot; Perry demanded.</p>
     *   <p>Steve waved a hand vaguely aloft into the greenish radiance of the lamp. &quot;All round. North, east, south and west. Land or sea. Adventures, Perry, are for the adventurous. Now, here we are, three able-bodied fellows fairly capable of looking after ourselves in most situations, tired of the humdrum life of Summer resorts. What's to prevent our spending a couple of months together and finding some adventures? Of course, we can't go to Africa and shoot lions and wart-hogs—whatever they may be,—and we can't fit out an Arctic exploration party and discover Ingersoll Land or Bush Inlet or Chapman's Passage, but we could have a mighty good time, I'd say, and, even if we didn't have many hair-breadth escapes, I'll bet it would beat chasing tennis balls and doing the Australian crawl and keeping our white shoes and trousers clean!&quot;</p>
     *   <p>&quot;We could be as dirty as we liked!&quot; sighed Perry ecstatically. &quot;Lead me to it!&quot;</p>
     *   <p>&quot;It sounds positively fascinating,&quot; drawled Joe, &quot;but just how would we go about it? My folks, for some unfathomable reason, think quite a lot of me, and I don't just see them letting me amble off like that; especially in—um—such disreputable company.&quot;</p>
     *   <p>&quot;I should think they'd be glad to be rid of you for a Summer,&quot; said Perry. &quot;Anyhow, let's make believe it's possible, fellows, and talk about it.&quot;</p>
     *   <p>&quot;Why isn't it possible?&quot; asked Steve. &quot;My folks would raise objections as well as yours, Joe, but I guess I could fetch them around. After all, there's no more danger than in staying at home and trying to break your neck driving an automobile sixty miles an hour. Let's really consider the scheme, fellows. I'm in earnest. I want to do it. What Perry said is just what I've been thinking without saying. Why, hang it, a fellow needs something of the sort to teach him sense and give him experience. This thing of hanging around a hotel porch all Summer makes a regular mollycoddle of a fellow. I'm for revolt!&quot;</p>
     *   <p>&quot;Hear! Hear!&quot; cried Perry enthusiastically. &quot;Revolution! <i>A bas la</i> Summer Resort! <i>Viva</i> Adventure!&quot;</p>
     *   <p>&quot;Shut up, idiot! Do you really mean it, Steve, or are you just talking? If you mean it, I'm with you to the last—um—drop of blood, old chap! I've always wanted to revolt about something, anyway. One of my ancestors helped throw the English breakfast tea into Boston Harbour. But I don't want to get all het up about this unless there's really something in it besides jabber.&quot;</p>
     *   <p>&quot;We start the first day of July,&quot; replied Steve decisively.</p>
     *   <p>&quot;Where for?&quot;</p>
     *   <p>&quot;That is the question, friends. Shall it be by land or sea?&quot;</p>
     *   <p>&quot;Land,&quot; said Joe.</p>
     *   <p>&quot;Sea,&quot; said Perry.</p>
     *   <p>&quot;The majority rules and I cast my vote with Perry. Adventures are more likely to be found on the water, I think, and it's adventures we are looking for.&quot;</p>
     *   <p>&quot;But I always get seasick,&quot; objected Joe. &quot;And when I'm seasick you couldn't tempt me with any number of adventures. I simply—um—don't seem to enthuse much at such times.&quot;</p>
     *   <p>&quot;You can take a lemon with you,&quot; suggested Perry cheerfully. &quot;My grandmother—&quot;</p>
     *   <p>Joe shook his head. &quot;They don't do you any good,&quot; he said sadly.</p>
     *   <p>&quot;Don't they! My grandmother—&quot;</p>
     *   <p>&quot;Bother your grandmother! How do we go to sea, Steve? Swim or—or how?&quot;</p>
     *   <p>&quot;We get my father's cruiser,&quot; replied Steve simply. &quot;She's a forty-footer and togged out like an ocean-liner. Has everything but a swimming-pool. She—&quot;</p>
     *   <p>&quot;Nix on the luxuries,&quot; interrupted Perry. &quot;The simple life for me. Let's hire an old moth-eaten sailboat—&quot;</p>
     *   <p>&quot;Nothing doing, Sweet Youth! If I'm to risk my life on the heaving ocean I want something under me. Besides, being seasick is rotten enough, anyhow, without having to roll around in the cock-pit of a two-by-twice sailboat. That cruiser listens well, Steve, but—um—will papa fall for it? If it was my father—&quot;</p>
     *   <p>&quot;I think he will,&quot; answered Steve seriously. &quot;Dad doesn't have much chance to use the boat himself, and this Summer he's likely to be in the city more than ever. The trouble is that the <i>Cockatoo</i> is almost too big for three of us to handle.&quot;</p>
     *   <p>&quot;Oh, piffle!&quot;</p>
     *   <p>&quot;It's so, though. I know the boat, Perry. She's pretty big when it comes to making a landing or picking up a mooring. If we were all fairly good seamen it might be all right, but I wouldn't want to try to handle the <i>Cockatoo</i> without a couple of sailors aboard.&quot;</p>
     *   <p>&quot;I once sailed a knockabout,&quot; said Perry.</p>
     *   <p>&quot;And I had a great-grandfather who was a sea captain,&quot; offered Joe encouragingly. &quot;What price great-grandfather?&quot;</p>
     *   <p>&quot;Don't see where your grandfather and Perry's grandmother come into this,&quot; replied Steve. &quot;How would it do if we gathered up two or three other fellows? The <i>Cockatoo</i> will accommodate six.&quot;</p>
     *   <p>&quot;Who could we get?&quot; asked Joe dubiously.</p>
     *   <p>&quot;Neil Fairleigh, for one.&quot;</p>
     *   <p>&quot;How about Han?&quot; offered Joe.</p>
     *   <p>&quot;Hanford always wants to boss everything,&quot; objected Perry.</p>
     *   <p>&quot;He knows boats, though, and so does Neil,&quot; said Steve. &quot;And they're both good fellows. That would make five of us, and five isn't too many. We can't afford to hire a cook, you know; at least, I can't; and someone will have to look after that end of it. Who can cook?&quot;</p>
     *   <p>&quot;I can't!&quot; Perry made the disclaimer with great satisfaction.</p>
     *   <p>&quot;No more can I,&quot; said Joe cheerfully. &quot;Let Neil be cook.&quot;</p>
     *   <p>&quot;I guess we'll all have to take a try at it. I dare say any of us can fry an egg and make coffee; and you can buy almost everything ready to eat nowadays.&quot;</p>
     *   <p>&quot;Tell you who's a whale of a cook,&quot; said Perry eagerly. &quot;That's Ossie Brazier. Remember the time we camped at Mirror Lake last Spring? Remember the flapjacks he made? M-mm!&quot;</p>
     *   <p>&quot;I didn't go,&quot; said Steve. &quot;What sort of a chap is Brazier? I don't know him very well.&quot;</p>
     *   <p>&quot;Well, Oscar's one of the sort who will do anything just as long as he thinks he doesn't have to,&quot; replied Joe. &quot;If we could get him to come along and tell him that he—um—simply must <i>not</i> ask to do the cooking, why—there you are!&quot;</p>
     *   <p>&quot;Merely a matter of diplomacy,&quot; laughed Steve. &quot;Well, we might have Brazier instead of Hanford—or Neil.&quot;</p>
     *   <p>&quot;Why not have them all if the boat will hold six?&quot; asked Joe. &quot;Seems to me the more we have the less each of us will have to do. I mean,&quot; he continued above the laughter, &quot;that—um—a division of labour—&quot;</p>
     *   <p>&quot;We get you,&quot; said Perry. &quot;But, say, I wish you'd stop talking about it, fellows. I'm going to be disappointed when I wake up and find it's only a bright and gaudy dream.&quot;</p>
     *   <p>&quot;It isn't a dream,&quot; answered Steve, &quot;unless you say so. I'll go, and I'll guarantee to get the <i>Cockatoo</i> without expense other than the cost of running her. If you and Joe can get your folks to let you come, and we can get hold of, say, two other decent chaps to fill the crew, why, we'll do it!&quot;</p>
     *   <p>&quot;Do you honestly mean it?&quot; demanded Perry incredulously. &quot;Gee, I'll get permission if I have to—to go without it!&quot;</p>
     *   <p>&quot;How about you, Joe?&quot;</p>
     *   <p>&quot;Um—I guess I could manage it. How long would we be gone?&quot;</p>
     *   <p>&quot;A month. Two, if you like. Start the first of July, or as soon after as possible, and get back in August.&quot;</p>
     *   <p>&quot;How much would it cost us?&quot; inquired Perry. &quot;I'm not a millionaire like you chaps.&quot;</p>
     *   <p>&quot;Wouldn't want to say offhand. We'd have to figure that. That's another reason for filling the boat up, though. The more we have the less everyone's share of the expense will be.&quot;</p>
     *   <p>&quot;Let's have the whole six, then, for money's scarce in my family these days. Let's make it a club, fellows. The Club of Six, or something of that sort. It sounds fine!&quot;</p>
     *   <p>&quot;Take in another fellow and call it The Lucky Seven,&quot; suggested Joe.</p>
     *   <p>&quot;We might not be lucky, though,&quot; laughed Steve. &quot;I'll tell you a better name.&quot;</p>
     *   <p>&quot;Shoot!&quot;</p>
     *   <p>&quot;The Adventure Club.&quot;</p>
     *  </body>
     *  </html>
     *
     */

    public List<ChapterBean> chapterList = new ArrayList<>();


    /**
     * 章节信息
     */
    public static class ChapterBean{
        String chapter;
        String title;
        public List<String> pList = new ArrayList<>();

        public String getChapter() {
            return chapter;
        }

        public void setChapter(String chapter) {
            this.chapter = chapter;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getpList() {
            return pList;
        }

        public void setpList(List<String> pList) {
            this.pList = pList;
        }
    }
}
