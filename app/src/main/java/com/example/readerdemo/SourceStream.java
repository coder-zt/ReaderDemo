package com.example.readerdemo;

import com.example.readerdemo.Reader.Config;

public class SourceStream {

    String englishData = "<html>\n" +
            "<head></head>\n" +
            "<body>\n" +
            "<h2>CHAPTER I</h2>\n" +
            "<p style=\"text-align: center\"><b>HOW IT STARTED</b></p>\n" +
            "<p>The Adventure Club had its inception, one evening toward the last of June, in Number 17 Sumner Hall, which is the oldest, most vine-hidden and most hallowed of the seven dormitories of Dexter Academy. It was a particularly warm evening, the two windows were wide open and the green-shaded light on the study table in the centre of the room had been turned low—Sumner prided itself on being conservative to the extent of gas instead of electricity and tin bathtubs instead of porcelain—and in the dim radiance the three occupants of the room were scarcely more than darker blurs.</p>\n" +
            "<p>Since final examinations had ended that afternoon and Graduation Day was only some twenty-eight hours away, none of the three was doing anything more onerous than yawning, and the yawn which came from Perry Bush, didn't sound as though it cost much of an effort. It was, rather, a comfortable, sleepy yawn, one that expressed contentment and relief, a sort of &quot;Glad-that's-over-and-I'm-still-alive&quot; yawn.</p>\n" +
            "<p>There was a window-seat under each casement in Number 17, and each was occupied by a recumbent figure. Perry was on the right-hand seat, his hands under his head and one foot sprawled on the floor, and Joe Ingersoll was in the other, his slim, white-trousered legs jack-knifed against the darker square of the open window. Near Joe, his feet tucked sociably against Joe's ribs, Steve Chapman, the third of the trio, reclined in a Morris chair. I use the word reclined advisedly, for Steve had lowered the back of the chair to its last notch, and to say that he was sitting would require a stretch of the imagination almost as long as Steve himself! Through the windows Steve could see the dark masses of the campus elms, an occasional star between the branches, and, by raising his head the fraction of an inch, the lights in the upper story of Hawthorne, across the yard. Somewhere under the trees outside a group of fellows were singing to the accompaniment of a wailing ukelele. They sang softly, so that the words floated gently up to the open casements just distinguishable:</p>\n" +
            "<p>&quot;Aw, shut up!&quot; muttered Perry, breaking the silence that had held them for several minutes. Joe Ingersoll laughed softly.</p>\n" +
            "<p>&quot;You don't seem to like the efforts of the—um—sweet-voiced choristers,&quot; he said in his slow way.</p>\n" +
            "<p>&quot;I don't like the sob-stuff,&quot; replied Perry resentfully. &quot;What's the use of rubbing it in? Why not let a fellow be cheerful after he has got through by the skin of his teeth and kicked his books under the bed? Gosh, some folks never want anyone to be happy!&quot; He raised himself by painful effort and peered out and down into the gloom. &quot;Sophs, I'll bet,&quot; he murmured, falling back again on the cushions. &quot;No one else would sit out here on the grass and sing school songs two days before the end. I hope that idiot singing second bass will get a brown-tail caterpillar down his neck!&quot;</p>\n" +
            "<p>&quot;The end!&quot; observed Steve Chapman. &quot;You say that as if we were all going to die the day after tomorrow, Perry! Cheer up! Vacation's coming!&quot;</p>\n" +
            "<p>&quot;Vacation be blowed!&quot; responded Perry. &quot;What's that amount to, anyway? Nothing ever happens to me in vacation. It's all well enough for you fellows to laugh. You're going up to college together in the Fall. I'm coming back to this rotten hole all alone!&quot;</p>\n" +
            "<p>&quot;Not quite alone, Sweet Youth,&quot; corrected Joe. &quot;There will be some four hundred other fellows here.&quot;</p>\n" +
            "<p>&quot;Oh, well, you know what I mean,&quot; said Perry impatiently. &quot;You and Steve will be gone, and I don't give a hang for any other chaps!&quot;</p>\n" +
            "<p>He ended somewhat defiantly, conscious that he had indulged in a most unmanly display of sentiment, and was glad that the darkness hid the confusion and heightened colour that followed the confession. Steve and Joe charitably pretended not to have noticed the lamentable exhibition of feeling, and a silence followed, during which the voices of the singers once more became audible.</p>\n" +
            "<p>&quot;<i>Cut it out!</i>&quot; Perry leaned over the windowsill and bawled the command down into the darkness. A defiant jeer answered him.</p>\n" +
            "<p>&quot;Don't be fresh,&quot; said Steve reprovingly. Perry mumbled and relapsed into silence. Presently, sighing as he changed his position, Joe said:</p>\n" +
            "<p>&quot;I believe Perry's right about vacation, Steve. Nothing much ever does happen to a fellow in Summer. I believe I've had more fun in school than at home the last six years.&quot;</p>\n" +
            "<p>The others considered the statement a minute. Then: &quot;Correct,&quot; said Steve. &quot;It's so, I guess. We're always crazy to get home in June and just as crazy to get back to school again in September, and I believe we all have more good times here than at home.&quot;</p>\n" +
            "<p>&quot;Of course we do,&quot; agreed Perry animatedly. &quot;Anyway, I do. Summers are all just the same. My folks lug me off to the Water Gap and we stay there until it's time to come back here. I play tennis and go motoring and sit around on the porch and—and—bathe—&quot;</p>\n" +
            "<p>&quot;Let's hope so,&quot; interpolated Joe gravely.</p>\n" +
            "<p>&quot;And nothing really interesting ever happens,&quot; ended Perry despairingly. &quot;Gee, I'd like to be a pirate or—or something!&quot;</p>\n" +
            "<p>&quot;Summers <i>are</i> rather deadly,&quot; assented Steve. &quot;We go to the seashore, but the place is filled with swells, and about all they do is change their clothes, eat and sleep. When you get ready for piracy, Perry, let me know, will you! I'd like to sign-on.&quot;</p>\n" +
            "<p>&quot;Put me down, too,&quot; said Joe. &quot;I've always had a—um—sneaking idea that I'd make a bully pirate. I'm naturally bloodthirsty and cruel. And I've got a mental list of folks who—um—I'd like to watch walk the plank!&quot;</p>\n" +
            "<p>&quot;Fellows of our ages have a rotten time of it, anyway,&quot; Perry grumbled. &quot;We're too old to play kids' games and too young to do anything worth while. What I'd like to do—&quot;</p>\n" +
            "<p>&quot;Proceed, Sweet Youth,&quot; Joe prompted after a moment.</p>\n" +
            "<p>&quot;Well, I'd like to—to start something! I'd like to get away somewhere and do things. I'm tired of loafing around in white flannels all day and keeping my hands clean. And I'm tired of dabbing whitewash on my shoes! Didn't you fellows ever think that you'd like to get good and dirty and not have to care? Wouldn't you like to put on an old flannel shirt and a pair of khaki trousers and some 'sneakers' and—and roll in the mud?&quot;</p>\n" +
            "<p>&quot;Elemental stuff,&quot; murmured Joe. &quot;He's been reading Jack London.&quot;</p>\n" +
            "<p>&quot;Well, that's the way I feel, lots of times,&quot; said Perry defiantly. &quot;I'm tired of being clean and white, and I'm tired of dinner jackets, and I'm sick to death of hotel porches! Gee, a healthy chap never was intended to lead the life of a white poodle with a pink ribbon around his neck! Me for some rough-stuff!&quot;</p>\n" +
            "<p>&quot;You're dead right, too,&quot; agreed Steve. &quot;That kind of thing is all right for Joe, of course. Joe's a natural-born 'fusser.' He's never happier than when he's dolled up in a sport-shirt and a lavender scarf and toasting marshmallows. But—&quot;</p>\n" +
            "<p>&quot;Is that so?&quot; inquired Joe with deep sarcasm. &quot;If I was half the 'fusser' you are—&quot;</p>\n" +
            "<p>&quot;What I want,&quot; interrupted Perry, warming to his theme, &quot;is adventure! I'd like to hunt big game, or discover the North Pole—&quot;</p>\n" +
            "<p>&quot;You're a year or two late,&quot; murmured Joe.</p>\n" +
            "<p>&quot;—or dig for hidden treasure!&quot;</p>\n" +
            "<p>&quot;You should—um—change your course of reading,&quot; advised Joe. &quot;Too much Roosevelt and Peary and Stevenson is your trouble. Read the classics for awhile—or the Patty Books.&quot;</p>\n" +
            "<p>&quot;That's all right, but you chaps are just the same, only you won't own up to it.&quot;</p>\n" +
            "<p>&quot;One of us will,&quot; said Steve; &quot;and does.&quot;</p>\n" +
            "<p>&quot;Make it two,&quot; yawned Joe. &quot;Beneath this—um—this polished exterior there beats a heart—I mean there flows the red blood of—&quot;</p>\n" +
            "<p>&quot;Look here, fellows, why not?&quot; asked Steve.</p>\n" +
            "<p>&quot;Why not what?&quot; asked Perry.</p>\n" +
            "<p>&quot;Why not have adventures? They say that all you have to do is look for them.&quot;</p>\n" +
            "<p>&quot;Don't you believe it! I've looked for them for years and I've never seen one yet.&quot; Perry swung his feet to the floor and sat up.</p>\n" +
            "<p>&quot;Well, not at Delaware Water Gap, naturally. You've got to move around, son. You don't find them by sitting all day with your feet on the rail of a hotel piazza.&quot;</p>\n" +
            "<p>&quot;Where do you find them, then?&quot; Perry demanded.</p>\n" +
            "<p>Steve waved a hand vaguely aloft into the greenish radiance of the lamp. &quot;All round. North, east, south and west. Land or sea. Adventures, Perry, are for the adventurous. Now, here we are, three able-bodied fellows fairly capable of looking after ourselves in most situations, tired of the humdrum life of Summer resorts. What's to prevent our spending a couple of months together and finding some adventures? Of course, we can't go to Africa and shoot lions and wart-hogs—whatever they may be,—and we can't fit out an Arctic exploration party and discover Ingersoll Land or Bush Inlet or Chapman's Passage, but we could have a mighty good time, I'd say, and, even if we didn't have many hair-breadth escapes, I'll bet it would beat chasing tennis balls and doing the Australian crawl and keeping our white shoes and trousers clean!&quot;</p>\n" +
            "<p>&quot;We could be as dirty as we liked!&quot; sighed Perry ecstatically. &quot;Lead me to it!&quot;</p>\n" +
            "<p>&quot;It sounds positively fascinating,&quot; drawled Joe, &quot;but just how would we go about it? My folks, for some unfathomable reason, think quite a lot of me, and I don't just see them letting me amble off like that; especially in—um—such disreputable company.&quot;</p>\n" +
            "<p>&quot;I should think they'd be glad to be rid of you for a Summer,&quot; said Perry. &quot;Anyhow, let's make believe it's possible, fellows, and talk about it.&quot;</p>\n" +
            "<p>&quot;Why isn't it possible?&quot; asked Steve. &quot;My folks would raise objections as well as yours, Joe, but I guess I could fetch them around. After all, there's no more danger than in staying at home and trying to break your neck driving an automobile sixty miles an hour. Let's really consider the scheme, fellows. I'm in earnest. I want to do it. What Perry said is just what I've been thinking without saying. Why, hang it, a fellow needs something of the sort to teach him sense and give him experience. This thing of hanging around a hotel porch all Summer makes a regular mollycoddle of a fellow. I'm for revolt!&quot;</p>\n" +
            "<p>&quot;Hear! Hear!&quot; cried Perry enthusiastically. &quot;Revolution! <i>A bas la</i> Summer Resort! <i>Viva</i> Adventure!&quot;</p>\n" +
            "<p>&quot;Shut up, idiot! Do you really mean it, Steve, or are you just talking? If you mean it, I'm with you to the last—um—drop of blood, old chap! I've always wanted to revolt about something, anyway. One of my ancestors helped throw the English breakfast tea into Boston Harbour. But I don't want to get all het up about this unless there's really something in it besides jabber.&quot;</p>\n" +
            "<p>&quot;We start the first day of July,&quot; replied Steve decisively.</p>\n" +
            "<p>&quot;Where for?&quot;</p>\n" +
            "<p>&quot;That is the question, friends. Shall it be by land or sea?&quot;</p>\n" +
            "<p>&quot;Land,&quot; said Joe.</p>\n" +
            "<p>&quot;Sea,&quot; said Perry.</p>\n" +
            "<p>&quot;The majority rules and I cast my vote with Perry. Adventures are more likely to be found on the water, I think, and it's adventures we are looking for.&quot;</p>\n" +
            "<p>&quot;But I always get seasick,&quot; objected Joe. &quot;And when I'm seasick you couldn't tempt me with any number of adventures. I simply—um—don't seem to enthuse much at such times.&quot;</p>\n" +
            "<p>&quot;You can take a lemon with you,&quot; suggested Perry cheerfully. &quot;My grandmother—&quot;</p>\n" +
            "<p>Joe shook his head. &quot;They don't do you any good,&quot; he said sadly.</p>\n" +
            "<p>&quot;Don't they! My grandmother—&quot;</p>\n" +
            "<p>&quot;Bother your grandmother! How do we go to sea, Steve? Swim or—or how?&quot;</p>\n" +
            "<p>&quot;We get my father's cruiser,&quot; replied Steve simply. &quot;She's a forty-footer and togged out like an ocean-liner. Has everything but a swimming-pool. She—&quot;</p>\n" +
            "<p>&quot;Nix on the luxuries,&quot; interrupted Perry. &quot;The simple life for me. Let's hire an old moth-eaten sailboat—&quot;</p>\n" +
            "<p>&quot;Nothing doing, Sweet Youth! If I'm to risk my life on the heaving ocean I want something under me. Besides, being seasick is rotten enough, anyhow, without having to roll around in the cock-pit of a two-by-twice sailboat. That cruiser listens well, Steve, but—um—will papa fall for it? If it was my father—&quot;</p>\n" +
            "<p>&quot;I think he will,&quot; answered Steve seriously. &quot;Dad doesn't have much chance to use the boat himself, and this Summer he's likely to be in the city more than ever. The trouble is that the <i>Cockatoo</i> is almost too big for three of us to handle.&quot;</p>\n" +
            "<p>&quot;Oh, piffle!&quot;</p>\n" +
            "<p>&quot;It's so, though. I know the boat, Perry. She's pretty big when it comes to making a landing or picking up a mooring. If we were all fairly good seamen it might be all right, but I wouldn't want to try to handle the <i>Cockatoo</i> without a couple of sailors aboard.&quot;</p>\n" +
            "<p>&quot;I once sailed a knockabout,&quot; said Perry.</p>\n" +
            "<p>&quot;And I had a great-grandfather who was a sea captain,&quot; offered Joe encouragingly. &quot;What price great-grandfather?&quot;</p>\n" +
            "<p>&quot;Don't see where your grandfather and Perry's grandmother come into this,&quot; replied Steve. &quot;How would it do if we gathered up two or three other fellows? The <i>Cockatoo</i> will accommodate six.&quot;</p>\n" +
            "<p>&quot;Who could we get?&quot; asked Joe dubiously.</p>\n" +
            "<p>&quot;Neil Fairleigh, for one.&quot;</p>\n" +
            "<p>&quot;How about Han?&quot; offered Joe.</p>\n" +
            "<p>&quot;Hanford always wants to boss everything,&quot; objected Perry.</p>\n" +
            "<p>&quot;He knows boats, though, and so does Neil,&quot; said Steve. &quot;And they're both good fellows. That would make five of us, and five isn't too many. We can't afford to hire a cook, you know; at least, I can't; and someone will have to look after that end of it. Who can cook?&quot;</p>\n" +
            "<p>&quot;I can't!&quot; Perry made the disclaimer with great satisfaction.</p>\n" +
            "<p>&quot;No more can I,&quot; said Joe cheerfully. &quot;Let Neil be cook.&quot;</p>\n" +
            "<p>&quot;I guess we'll all have to take a try at it. I dare say any of us can fry an egg and make coffee; and you can buy almost everything ready to eat nowadays.&quot;</p>\n" +
            "<p>&quot;Tell you who's a whale of a cook,&quot; said Perry eagerly. &quot;That's Ossie Brazier. Remember the time we camped at Mirror Lake last Spring? Remember the flapjacks he made? M-mm!&quot;</p>\n" +
            "<p>&quot;I didn't go,&quot; said Steve. &quot;What sort of a chap is Brazier? I don't know him very well.&quot;</p>\n" +
            "<p>&quot;Well, Oscar's one of the sort who will do anything just as long as he thinks he doesn't have to,&quot; replied Joe. &quot;If we could get him to come along and tell him that he—um—simply must <i>not</i> ask to do the cooking, why—there you are!&quot;</p>\n" +
            "<p>&quot;Merely a matter of diplomacy,&quot; laughed Steve. &quot;Well, we might have Brazier instead of Hanford—or Neil.&quot;</p>\n" +
            "<p>&quot;Why not have them all if the boat will hold six?&quot; asked Joe. &quot;Seems to me the more we have the less each of us will have to do. I mean,&quot; he continued above the laughter, &quot;that—um—a division of labour—&quot;</p>\n" +
            "<p>&quot;We get you,&quot; said Perry. &quot;But, say, I wish you'd stop talking about it, fellows. I'm going to be disappointed when I wake up and find it's only a bright and gaudy dream.&quot;</p>\n" +
            "<p>&quot;It isn't a dream,&quot; answered Steve, &quot;unless you say so. I'll go, and I'll guarantee to get the <i>Cockatoo</i> without expense other than the cost of running her. If you and Joe can get your folks to let you come, and we can get hold of, say, two other decent chaps to fill the crew, why, we'll do it!&quot;</p>\n" +
            "<p>&quot;Do you honestly mean it?&quot; demanded Perry incredulously. &quot;Gee, I'll get permission if I have to—to go without it!&quot;</p>\n" +
            "<p>&quot;How about you, Joe?&quot;</p>\n" +
            "<p>&quot;Um—I guess I could manage it. How long would we be gone?&quot;</p>\n" +
            "<p>&quot;A month. Two, if you like. Start the first of July, or as soon after as possible, and get back in August.&quot;</p>\n" +
            "<p>&quot;How much would it cost us?&quot; inquired Perry. &quot;I'm not a millionaire like you chaps.&quot;</p>\n" +
            "<p>&quot;Wouldn't want to say offhand. We'd have to figure that. That's another reason for filling the boat up, though. The more we have the less everyone's share of the expense will be.&quot;</p>\n" +
            "<p>&quot;Let's have the whole six, then, for money's scarce in my family these days. Let's make it a club, fellows. The Club of Six, or something of that sort. It sounds fine!&quot;</p>\n" +
            "<p>&quot;Take in another fellow and call it The Lucky Seven,&quot; suggested Joe.</p>\n" +
            "<p>&quot;We might not be lucky, though,&quot; laughed Steve. &quot;I'll tell you a better name.&quot;</p>\n" +
            "<p>&quot;Shoot!&quot;</p>\n" +
            "<p>&quot;The Adventure Club.&quot;</p>\n" +
            "</body>\n" +
            "</html>";

    String EN_CNData = "<html>\n" +
            " <head></head>\n" +
            " <body>\n" +
            "  <p id=\"p0\"><span id=\"s0\" start_time=\"00:00:00.000\" end_time=\"00:00:15.500\" chinese=\"从前有一个非常富有的商人，他有六个孩子，三个儿子和三个女儿；他是一个有见识的人，不惜一切代价教育他们，却给他们各种各样的主人。\" official_chinese=\"暂无官方翻译\">There was once a very rich merchant, who had six children, three sons, and three daughters; being a man of sense, he spared no cost for their education, but gave them all kinds of masters. </span><span id=\"s1\" start_time=\"00:00:15.500\" end_time=\"00:00:33.100\" chinese=\"他的女儿们都非常英俊，尤其是最小的女儿；她小的时候，大家都很羡慕她，叫她小美人；这样，她长大后，仍然以美人的名字去，这让她的姐妹们都很嫉妒。\" official_chinese=\"暂无官方翻译\">His daughters were extremely handsome, especially the youngest; when she was little, everybody admired her, and called her The little Beauty; so that, as she grew up, she still went by the name of Beauty, which made her sisters very jealous.</span></p>\n" +
            "  <p id=\"p1\"><span id=\"s2\" start_time=\"00:00:33.100\" end_time=\"00:00:37.800\" chinese=\"最小的，因为她很英俊，也比她的姐妹们好。\" official_chinese=\"暂无官方翻译\">The youngest, as she was handsome, was also better than her sisters.</span></p>\n" +
            "  <p id=\"p2\"><span id=\"s3\" start_time=\"00:00:37.800\" end_time=\"00:00:42.500\" chinese=\"两位老大非常骄傲，因为他们很有钱。\" official_chinese=\"暂无官方翻译\">The two eldest had a great deal of pride, because they were rich. </span><span id=\"s4\" start_time=\"00:00:42.500\" end_time=\"00:00:52.100\" chinese=\"他们装出可笑的样子，不去拜访其他商人的女儿，也不跟任何人来往，只跟有品质的人来往。\" official_chinese=\"暂无官方翻译\">They gave themselves ridiculous airs, and would not visit other merchants' daughters, nor keep company with any but persons of quality. </span><span id=\"s5\" start_time=\"00:00:52.100\" end_time=\"00:01:05.000\" chinese=\"他们每天出去参加欢乐聚会、舞会、戏剧、音乐会等，嘲笑他们最小的妹妹，因为她把大部分时间花在读好书上。\" official_chinese=\"暂无官方翻译\">They went out every day upon parties of pleasure, balls, plays, concerts, etc. And laughed at their youngest sister, because she spent the greatest part of her time in reading good books. </span><span id=\"s6\" start_time=\"00:01:05.000\" end_time=\"00:01:18.600\" chinese=\"众所周知，他们将有大笔财富，几个著名的商人向他们提出了他们的地址，但两个长辈说，他们永远不会结婚，除非他们能遇到一个公爵，或至少一个伯爵。\" official_chinese=\"暂无官方翻译\">As it was known that they were to have great fortunes, several eminent merchants made their addresses to them; but the two eldest said they would never marry, unless they could meet with a Duke, or an Earl at least.</span></p>\n" +
            "  <p id=\"p3\"><span id=\"s7\" start_time=\"00:01:18.600\" end_time=\"00:01:28.900\" chinese=\"美女非常客气地感谢向她求爱的人，并告诉他们她还太小，还没有结婚，但她选择和父亲再多待几年。\" official_chinese=\"暂无官方翻译\">Beauty very civilly thanked them that courted her, and told them she was too young yet to marry, but chose to stay with her father a few years longer.</span></p>\n" +
            "  <p id=\"p4\"><span id=\"s8\" start_time=\"00:01:28.900\" end_time=\"00:01:44.300\" chinese=\"商人突然失去了所有的财产，除了离城里很远的一所乡村小房子外，他含泪告诉他的孩子们，他们必须去那里工作谋生。\" official_chinese=\"暂无官方翻译\">All at once the merchant lost his whole fortune, excepting a small country-house at a great distance from town, and told his children, with tears in his eyes, they must go there and work for their living. </span><span id=\"s9\" start_time=\"00:01:44.300\" end_time=\"00:02:02.200\" chinese=\"两个长辈回答说，他们不会离开这座城市，因为他们有好几个情人，虽然他们没有财产，但他们肯定很乐意拥有他们；但在这一点上，他们错了，因为他们的情人在他们贫穷的时候轻视和抛弃了他们。\" official_chinese=\"暂无官方翻译\">The two eldest answered, that they would not leave the town, for they had several lovers, who they were sure would be glad to have them, though they had no fortune; but in this they were mistaken, for their lovers slighted and forsook them in their poverty.</span></p>\n" +
            "  <p id=\"p5\"><span id=\"s10\" start_time=\"00:02:02.200\" end_time=\"00:02:19.000\" chinese=\"因为他们不是因为骄傲而被宠爱的，所以大家都说：“他们不值得同情，我们很高兴看到他们的骄傲被贬低了，让他们走吧，在挤奶和照料奶牛方面给自己一个良好的姿态。\" official_chinese=\"暂无官方翻译\">As they were not beloved on account of their pride, everybody said, &quot;they do not deserve to be pitied, we are glad to see their pride humbled, let them go and give themselves quality airs in milking the cows and minding their dairy. </span><span id=\"s11\" start_time=\"00:02:19.000\" end_time=\"00:02:33.200\" chinese=\"但是，（他们又说，）我们非常关心美貌，她是一个如此迷人、脾气温和的人，对穷人如此和蔼可亲，性情如此和蔼可亲。\" official_chinese=\"暂无官方翻译\">But, (added they,) we are extremely concerned for Beauty, she was such a charming, sweet-tempered creature, spoke so kindly to poor people, and was of such an affable, obliging disposition. </span><span id=\"s12\" start_time=\"00:02:33.200\" end_time=\"00:02:33.200\" chinese=\"\" \"=\"\" official_chinese=\"暂无官方翻译\">&quot;</span></p>\n" +
            "  <p id=\"p6\"><span id=\"s13\" start_time=\"00:02:33.200\" end_time=\"00:02:50.200\" chinese=\"不，有几个绅士会娶她，尽管他们知道她一分钱也没有；但她告诉他们，她想不起在她可怜的父亲不幸时离开他，而是决心和他一起到乡下去安慰和照顾他。\" official_chinese=\"暂无官方翻译\">Nay, several gentlemen would have married her, though they knew she had not a penny; but she told them she could not think of leaving her poor father in his misfortunes, but was determined to go along with him into the country to comfort and attend him.</span></p>\n" +
            "  <p id=\"p7\"><span id=\"s14\" start_time=\"00:02:50.200\" end_time=\"00:03:05.600\" chinese=\"可怜的美人起初为失去财产而伤心不已，“可是，”她自言自语地说，“如果我哭得这么厉害，那事情就不会好起来，我必须努力使自己没有财产而快乐起来。\" official_chinese=\"暂无官方翻译\">Poor Beauty at first was sadly grieved at the loss of her fortune; &quot;but, (she said to herself,) were I to cry ever so much, that would not make things better, I must try to make myself happy without a fortune. </span><span id=\"s15\" start_time=\"00:03:05.600\" end_time=\"00:03:05.600\" chinese=\"\" \"=\"\" official_chinese=\"暂无官方翻译\">&quot; </span><span id=\"s16\" start_time=\"00:03:05.600\" end_time=\"00:03:21.000\" chinese=\"他们到了自己的乡间小屋，商人和他的三个儿子专心耕种，到了早晨四点，美貌就起来，急忙打扫屋子，为全家准备早餐。\" official_chinese=\"暂无官方翻译\">When they came to their country-house, the merchant and his three sons applied themselves to husbandry and tillage; and Beauty rose at four in the morning, and made haste to have the house clean, and breakfast ready for the family.</span></p>\n" +
            "  <p id=\"p8\"><span id=\"s17\" start_time=\"00:03:21.000\" end_time=\"00:03:32.000\" chinese=\"一开始她觉得很困难，因为她不习惯当佣人，但不到两个月，她就变得比以前更强壮、更健康了。\" official_chinese=\"暂无官方翻译\">In the beginning she found it very difficult, for she had not been used to work as a servant; but in less than two months she grew stronger and healthier than ever. </span><span id=\"s18\" start_time=\"00:03:32.000\" end_time=\"00:03:38.900\" chinese=\"做完工作后，她一边看书，一边弹大键琴，或者一边旋转一边唱歌。\" official_chinese=\"暂无官方翻译\">After she had done her work, she read, played on the harpsichord, or else sung whilst she spun.</span></p>\n" +
            "  <p id=\"p9\"><span id=\"s19\" start_time=\"00:03:38.900\" end_time=\"00:03:52.200\" chinese=\"恰恰相反，她的两个姐姐不知道如何打发时间；她们十点钟起床，整天无所事事，只是闲逛，为失去了她们的漂亮衣服和相识而感到惋惜。\" official_chinese=\"暂无官方翻译\">On the contrary, her two sisters did not know how to spend their time; they got up at ten, and did nothing but saunter about the whole day, lamenting the loss of their fine clothes and acquaintance. </span><span id=\"s20\" start_time=\"00:03:52.200\" end_time=\"00:04:02.800\" chinese=\"“只要看看我们最小的妹妹就知道了，”他们一个接一个地说，“她是一个多么可怜、多么愚蠢、多么卑鄙的人，她能满足于这种不幸的处境。\" official_chinese=\"暂无官方翻译\">&quot;Do but see our youngest sister, (said they one to the other,) what a poor, stupid mean-spirited creature she is, to be contented with such an unhappy situation. </span><span id=\"s21\" start_time=\"00:04:02.800\" end_time=\"00:04:02.800\" chinese=\"\" \"=\"\" official_chinese=\"暂无官方翻译\">&quot;</span></p>\n" +
            "  <p id=\"p10\"><span id=\"s22\" start_time=\"00:04:02.800\" end_time=\"00:04:24.300\" chinese=\"这位好商人的看法大不相同，他很清楚，美貌在她身上和心灵上都使她的姐妹们熠熠生辉，他钦佩她的谦逊、勤劳和耐心，因为她的姐妹们不仅把家里的一切工作都留给她去做，而且每时每刻都在侮辱她。\" official_chinese=\"暂无官方翻译\">The good merchant was of a quite different opinion; he knew very well that Beauty out-shone her sisters, in her person as well as her mind, and admired her humility, industry, and patience; for her sisters not only left her all the work of the house to do, but insulted her every moment.</span></p>\n" +
            " </body>\n" +
            "</html>";
    private onDataCallback mCallback;

    public void requestData(int type){
        if (mCallback != null) {
            switch (type){
                case Config.ENG:
                    mCallback.dataCallback(englishData);
                    break;
                case Config.ENG_CN:
                        mCallback.dataCallback(EN_CNData);
                        break;
            }

        }
    }

    public void setOnDataCallback(onDataCallback callback){
        mCallback = callback;
    }



    public interface onDataCallback{
        void dataCallback(String data);
    }
}
