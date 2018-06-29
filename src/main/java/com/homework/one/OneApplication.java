package com.homework.one;

import com.homework.one.bean.Book;
import com.homework.one.bean.Role;
import com.homework.one.bean.User;
import com.homework.one.responsitory.BookRepository;
import com.homework.one.responsitory.RoleRepository;
import com.homework.one.responsitory.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class OneApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneApplication.class, args);
	}

    @Bean
    CommandLineRunner init(BookRepository bookRepository, RoleRepository roleRepository, UserRepository userRepository) {
        return (args) -> {
            //init implement
            //UserTestData
            User one = new User("101", "101","JUNF");
            User two = new User("admin", "admin","admin");
            Role user = new Role("USER");
            Role admin = new Role("ADMIN");
            Set<Role> roles1 = new HashSet<Role>();
            Set<Role> roles2 = new HashSet<Role>();
            roles1.add(user);
            roles2.add(admin);

            //书本测试
            Book book1= new Book("9787020026906", "白鹿原","陈忠实 ","文学", "这是一部渭河平原五十年变迁的雄奇史诗，一轴中国农村班斓多彩、触目惊心的长幅画卷。主人公六娶六丧，神秘的序曲预示着不祥。一个家族两代子孙，为争夺白鹿原的统治代代争斗不已，上演了一幕幕惊心动魄的活剧：巧取风水地，恶施美人计，孝子为匪，亲翁杀媳，兄弟相煎，情人反目……大革命、日寇入侵、三年内战，白鹿原翻云覆雨，王旗变幻，家仇国恨交错缠结，冤冤相报代代不已……古老的土地在新生的阵痛中颤栗。厚重深邃的思想内容，复杂多变的人物性格，跌宕曲折的故事情节，绚丽多彩的风土人情，形成作品鲜明的艺术特色和令人震撼的真实感。");
            book1.setBookPhoto(book1.getBookISBN()+".jpg");
            Book book2= new Book("9787020044528","平凡的世界", "路遥 ","文学", "这是一部全景式地表现中国当代城乡社会生活的长篇小说。全书共三部。作者在近十年间广阔背景上，通过复杂的矛盾纠葛，刻划了社会各阶层众多普通人的形象。劳动与爱情，挫折与追求，痛苦与欢乐，日常生活与巨大社会冲突，纷繁地交织在一起，深刻地展示了普通人在大时代历史进程中所走过的艰难曲折的道路。");
            book2.setBookPhoto(book2.getBookISBN()+".jpg");
            Book book3= new Book("9787020056309","东方快车谋杀案", "阿加莎·克里斯蒂","悬疑", "午夜过后，一场大雪迫使东方快车停了下来。这辆豪华列车整年都处于满员状态。但那天早上却发现少了一名乘客。一个美国人死在了他的包厢里，他被刺了十二刀，可他包厢的门却是反锁着的。");
            book3.setBookPhoto(book3.getBookISBN()+".jpg");
            Book book4= new Book("9787020065394","无人生还", " 阿加莎·克里斯蒂 ","悬疑", "十个互不相识的人，被富有的欧文先生邀请到了印地安岛上的私人别墅里。晚餐后，一个神秘的声音揭开了人们心中所各自隐藏着的可怕秘密。当天晚上，年轻的马斯顿先生离奇死去，古老的童谣就像诅咒一样笼罩着所有人，似乎有一双神秘的眼镜在时刻窥视着这场死亡游戏，到访者就像消失的印地安小瓷人一样一个又一个的走向死神。");
            book4.setBookPhoto(book4.getBookISBN()+".jpg");
            book1.setUser(one);
            book2.setUser(one);
            book3.setUser(one);
            book4.setUser(one);
            List<Book> books = new ArrayList<Book>();
            books.add(book1);
            books.add(book2);
            books.add(book3);
            books.add(book4);

            one.setRoles(roles1);
            one.setBooks(books);
            userRepository.save(one);
            two.setRoles(roles2);
            userRepository.save(two);

            //when save user, ROLE is saved too.
            //roleRepository.save(user);
            //roleRepository.save(admin);

            //BookTestData
            Book book = new Book("9787562017677","法理学 : 法律哲学与法律方法"," E.博登海默","本书把散见于1940年《法理学》一书中的有关法理学思想发展的历史资料集中在第一部分。本书第二部发和第三部分中对一般法律理论的实质性问题所作的论述，乃是以某些蕴含在我研究法理学问题的进路中的哲学假设和方法论假设为基础的。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787503678622","法律与革命", "伯尔曼 ","本书讲述的是下面的历史：曾经有一种称做“西方的”文明；这种文明发展出了独特的“法律的”制度、价值和概念；这些西方的法律制度、价值和概念被有意识地世代相传数个世纪，由此而开始形成一种“传统”；西方法律传统产生于一次“革命”，它在后来数个世纪的过程中被革命周期性地打断和改造；在20世纪，西方法律传统的革命危机比历史上任何其他时期都要大，某些人相信这种危机实质上已导致了这种传统的终结");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787301150894","经济学原理","N·格里高利·曼昆 ","《经济学原理(第5版):微观经济学分册》是世界上最流行的经济学教材！其英文原版现已被哈佛大学、耶鲁大学、斯坦福大学等美国600余所大学用作经济学原理课程的教材迄今为止它已被翻译成20种语言在全世界销售100多万册！");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787308116268","历代经济变革得失","吴晓波","两千七百年前，春秋时期的齐国宰相管仲改制变法，使得齐国一跃成为霸主，傲视群雄；公元1069年，王安石在宋神宗的支持下，推行新法，一时国库充实，北宋积贫积弱的局面为之缓解；公元1978年，总设计师邓小平开始实施改革开放政策，百年积弱的中国经济再度崛起，重回强国之列。在两千多年的时间里，中国经历了十数次重大的经济变革，每一次变法，都顺应社会发展而发生，也都对历史进程产生了重大影响。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787536460300", "重力使命","哈尔·克莱蒙特 ","科幻","一个重力大大超过地球的星球引起了人类的兴趣，但在高重力的影响下，人类的探测器不幸坠落。人类只得求助于形同蜈蚣的当地人，引导这些远未进入技术文明时代的土著长途跋涉，找回探测器。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787536474710","路边野餐","鲍里斯·斯特鲁伽茨基","科幻","年轻的瑞德是一名研究助理，他的工作是在外星人的造访带里搜寻外星物品、研究外星科技。这片造访带辐射强烈，污染严重，对人类安全有着极大威胁。为此，联合国封锁了所有的造访带，只有持通行证的研究人员才可以进入。然而，仍有一些人甘冒生命危险私自潜入，他们是赏金猎人，是潜行者，游走在寸草不生、危机重重的造访带里，为人们趋之若鹜的高科技外星垃圾不惜献出生命。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787108015280", "中国历代政治得失","钱穆","《中国历代政治得失》为作者的专题演讲合集，分别就中国汉、唐、宋、明、清五代的政府组织、百官职权、考试监察、财经赋税、兵役义务等种种政治制度作了提要勾玄的概观与比照，叙述因革演变，指陈利害得失。既高屋建瓴地总括了中国历史与政治的精要大义，又点明了近现代国人对传统文化和精神的种种误解。言简意赅，语重心长，实不失为一部简明的“中国政治制度史”。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787213034435", "大转型","卡尔·波兰尼","政治","在这部关于经济史和社会理论的经典著作中，卡尔·波兰尼分析了工业革命的大转型带来的经济和社会的巨大变化。他的分析不仅阐述了自我调适的自由市场的缺陷，而且阐述了资本主义市场带来的可怕的社会后果。新的序言和导言揭示了在全球化和自由贸易时代波兰尼的精辟分析所具有的新的价值。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787544752473", "你一生的故事","特德·姜","小说","一瞥之下，过去与未来轰轰然同时并至，我的意识成为长达半个世纪的灰烬，时间未至已成灰。五十年诸般纷纭并发眼底，我的余生尽在其中。还有，你的一生。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787544290678", "南方高速","胡里奥·科塔萨尔 ","小说","当现实还有别的可能时，为什么要接受司空见惯的某一种呢？阅读科塔萨尔，就是轻快地掉进爱丽丝的兔子洞，是惊奇地通过衣橱走向纳尼亚，是缓慢但不失优雅地步入普通世界中更幽微的那片天地。现实与幻想交织，时空秩序犹如万花筒一样充满了颠覆的可能，日常生活和美丽幻想中的自我时而重合时而冲突，冥冥中存在奇异、神秘、荒诞的联系。与科塔萨尔每一次相遇，都是一段独一无二的飞驰，一场华丽自由的冒险。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
            book = new Book("9787209041065", "设计中的设计","原研哉 ","设计到底是什么？作为一名从业二十余年并且具有世界影响的设计师，原研哉对自己提出了这样一个问题。为了给出自己的答案，他走了那么长的路，做了那么多的探索。“RE-DESIGN——二十一世纪的日常用品再设计”展真是一个有趣的展览，但又不仅仅是有趣，它分明是为我们揭示了“日常生活”所具有的无限可能性。若我们能以满怀新鲜的眼神去观照日常，“设计”的意义定会超越技术的层面，为我们的生活观和人生观注入力量。");
            book.setBookPhoto(book.getBookISBN()+".jpg");
            bookRepository.save(book);
        };
    }
}
