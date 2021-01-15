package borys.parser;

public interface ConstantStringValues {

	String SCRIPT_DELETION_EXPECTATION = "<body>\r\n" + "<li class=\"dropdown\">\r\n"
			+ "<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Plugin extensions <b class=\"caret\"></b></a>\r\n"
			+ "<ul class=\"dropdown-menu\">\r\n"
			+ "<li><a href=\"#!/extensions\">Building an extension plugin</a></li>\r\n"
			+ "<li><a href=\"#!/custom-checks\">Custom Checkstyle modules</a></li>\r\n"
			+ "<li><a href=\"#!/builtin-config\">Custom built-in Checkstyle configurations</a></li>\r\n"
			+ "<li><a href=\"#!/custom-filters\">Custom plugin filters</a></li>\r\n" + "</ul>\r\n" + "</li>\r\n"
			+ "<li class=\"dropdown\">\r\n"
			+ "<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Huh? Get help <b class=\"caret\"></b></a>\r\n"
			+ "<ul class=\"dropdown-menu\">\r\n" + "<li><a href=\"#!/faq\">FAQ</a></li>\r\n"
			+ "<li class=\"divider\"></li>\r\n"
			+ "<li><a target=\"_blank\" href=\"https://github.com/checkstyle/eclipse-cs/issues\">File a bug/feature request <i\r\n"
			+ "class=\"fa fa-external-link\"></i></a></li>\r\n" + "</ul>\r\n" + "</li>\r\n" + "</ul>\r\n" + "</div>\r\n"
			+ "</header>\r\n" + "<div id=\"main\" role=\"main\" data-ng-view=\"\"> </div>\r\n" + "</div>\r\n"
			+ "<!--  javascript -->\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "</body>";

	String TEXT_SEPARATION_EXPECTATION = "Источник — https://ru.wikipedia.org/w/index.php?title=Заглавная_страница & oldid=111552052  Скрытая категория: Википедия:Страницы с ежечасно очищаемым кэшем      Навигация      Персональные инструменты    Вы не представились системе Обсуждение Вклад Создать учётную запись Войти   ";
	String TEXT_FOR_WORDS_COUNT = "Our core Java programming tutorial is designed for students and working professionals. Java is an object-oriented, class-based, concurrent, secured and general-purpose computer-programming language. It is a widely used robust technology.\r\n"
			+ "Java is a programming language and a platform. Java is a high level, robust, object-oriented and secure programming language.\r\n"
			+ "Java was developed by Sun Microsystems (which is now the subsidiary of Oracle) in the year 1995. James Gosling is known as the father of Java. Before Java, its name was Oak. Since Oak was already a registered company, so James Gosling and his team changed the Oak name to Java.\r\n"
			+ "Platform: Any hardware or software environment in which a program runs, is known as a platform. Since Java has a runtime environment (JRE) and API, it is called a platform.\r\n";
}
