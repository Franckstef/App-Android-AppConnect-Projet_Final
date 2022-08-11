package com.colibri.appconnect;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements NewsFeedAdapter.OnItemClicked {

    public HomeFragment() {}

    ViewPager viewPager;
    int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8, R.drawable.img_9, R.drawable.img_10, R.drawable.img_11, R.drawable.img_12};
    private final ArrayList<Integer> ImagesArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private View view;
    private OnButtonClickedListener mCallback;
    HomeActivity homeActivity;

    public interface OnButtonClickedListener {
        void onButtonClicked(View view, Bundle bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), images);
        viewPager.setAdapter(viewPagerAdapter);

        ArrayList<News> list= new ArrayList();
        list.add(new News("news_acceptabilite",
                "L'importance de mettre en place une démarche d’acceptabilité sociale dans vos projets",
                "En 1967, une importante infrastructure québécoise est inaugurée : le pont-tunnel Louis-Hippolyte-La Fontaine. Réputée pour sa fiabilité sur le plan de la mécanique et pour sa durabilité, cette infrastructure est néanmoins représentative d’une époque révolue en ce qui concerne la conception d’un projet. Les travaux, aujourd’hui en rupture avec les pratiques courantes en matière d’acceptabilité sociale, auraient certainement rencontré plus d’un obstacle.\n",
                "Cet ouvrage en béton précontraint est effectivement construit à une époque où environnement et collectivités s’adaptaient aux projets, et non l’inverse. Érigé sur les berges du fleuve Saint-Laurent, le pont-tunnel a alors engendré des impacts environnementaux majeurs. Répercussions sous-estimées, voire ignorées, à une époque où peu de considération était portée à l’environnement\n" +
                        "\n" +
                        "« C’est devenu un critère incontournable », mentionne Mathieu Pelletier, vice-président, Acceptabilité sociale et relations avec les communautés chez Pilote groupe-conseil. En effet, l’environnement est de nos jours un élément incontournable dans l’adoption d’un projet par la communauté, et un projet conçu tel que celui du pont-tunnel Louis-Hippolyte-La Fontaine se disqualifierait dans l’opinion publique en regard des dommages écologiques. Le facteur environnemental est un pilier du principe de l’acceptabilité sociale, au même titre que l’ESG (environnement, social et gouvernance).\n" +
                        "\n" +
                        "Dans ce type d’investissement, un projet n’est plus dirigé par une simple logique comptable : il prend aussi en compte les facteurs environnementaux et sociaux. Par sa nature même, l’investissement responsable prend sa pleine signification en appliquant le principe d’acceptabilité sociale dans sa vision du patrimoine bâti. Dorénavant, un projet ne s’impose plus de force ou sans consultation préalable dans un habitat naturel ou au sein d’une collectivité : il est élaboré en collaboration avec elle. La société doit effectivement être prise en compte dans ses nuances et ses particularités, son historique et ses enjeux, ainsi que ses préoccupations et ses besoins. Il s’agit d’un processus qui permet d’évaluer la faisabilité d’un projet et dont la finalité recherchée est l’approbation majoritaire – et non unanime, précisons-le – d’un projet et qui accompagne toutes ses phases de réalisation, et ce, jusqu’à l’après-projet\n" +
                        "\n" +
                        "Mais par où commencer ? « Dans un premier temps, la démarche doit être entamée en amont du projet, le plus rapidement possible », mentionne Mathieu Pelletier. Le spécialiste en aménagement du territoire et en développement régional pose les bases d’une série d’actions à effectuer dans une démarche concrète d’acceptabilité sociale. Le dialogue doit être engagé dès que possible, car un projet, même bien ficelé, comporte des pièges et peut être voué à l’échec.\n" +
                        "\n" +
                        "Une étude préalable de la communauté visant à développer une bonne compréhension du milieu et du contexte d’accueil du projet est primordiale. Qui sont les parties prenantes ? Quelles sont ses caractéristiques sociogéographiques et socioéconomiques ? Le projet s’implantet- il dans un milieu où la population dépend des ressources naturelles qui s’y trouvent ? Y a-t-il, au sein de la communauté, un biais en faveur de la main-d’oeuvre locale? Quel est l’historique des projets similaires précédents ? La consultation des membres de la communauté bonifie cette recherche préalable.\n" +
                        "\n" +
                        "Une meilleure compréhension initiale du milieu d’accueil permet ainsi de présenter un canevas du projet déjà plus en phase avec le milieu dans lequel il s’inscrira à l’étape suivante, soit celle de l’information, de l’évaluation et de la consultation. Cette phase permet quant à elle de bonifier ou de modifier le projet. Des négociations peuvent intervenir à ce stade. L’intégration d’éléments observés par les parties prenantes peut d’ailleurs encourager leur adhésion au projet. « Les participants aux négociations peuvent même en devenir des ambassadeurs », ajoute Mathieu Pelletier.\n" +
                        "\n" +
                        "Deux grandes règles régissent une démarche d’acceptabilité sociale. À toutes les étapes de conception, le dialogue doit être maintenu. Celui-ci doit débuter le plus tôt possible et l’information, pour sa part, doit être communiquée en tout temps. Transparente, elle ne se limite pas qu’à une simple transmission, mais se traduit par un échange. Nul besoin de mentionner que les stratégies propagandistes et de storytelling sont contraires au principe d’acceptabilité sociale. Un processus qui est mené jusqu’au bout permet donc de maintenir le contact avec la communauté, et ce, au-delà d’une clôture de chantier.\n" +
                        "\n" +
                        "L’après-projet peut en effet être riche de renseignements nouveaux émanant de toutes les parties prenantes. Parmi les autres pratiques de suivi de projet, un comité peut être mis sur pied afin d’assurer un suivi auprès de la communauté après l’inauguration du chantier.",
                "08/08/22"));
        list.add(new News("news_transport",
                "S'approvisionner en produits locaux par l’intermédiaire d’un circuit court",
                "Face aux problèmes d’approvisionnement provoqués par la COVID-19, Matériaux Direct, un détaillant en matériaux de construction, a concentré ses efforts sur l’approvisionnement de produits locaux par l’intermédiaire d’un circuit court. Mais comment cette initiative peut-elle servir d’exemple dans le milieu de la construction ?\n",
                "Depuis six ans, son propriétaire, Maxime Bossinotte, répond aux besoins de l’industrie en ce qui a trait à l’ossature du bâtiment et à l’aménagement extérieur. Mais depuis mars 2020, la COVID-19 agit comme une chape de plomb et sème quelques embuches sur son sillage. À mesure que les matériaux de construction se font de plus en plus rares, les délais se prolongent.\n" +
                        "\n" +
                        "Dans ce secteur d’activité où le volume des stocks est crucial, il opte alors pour la rapidité. « Avant l’arrivée de la COVID-19, j’achetais le bois déjà traité et je ne mesurais aucunement la provenance des matériaux. Quand je me suis aperçu des kilomètres en trop qu’effectuait le bois, j’ai réalisé que je pouvais m’approvisionner autrement. »\n" +
                        "\n" +
                        "Habitué à recevoir son bois traité depuis la Gaspésie, Maxime Bossinotte développe un intérêt pour la suppression des circuits longs. Ainsi, l’entrepreneur contacte deux scieries et une usine de traitement du bois. L’organisation de cette logistique le démarque rapidement de la compétition avec l’assurance de contrôler la qualité de ses matériaux, car il sait comment ils ont été traités : « Certes, la préparation du bois prend plus de temps d’expédition que si j’avais le produit clés en main, mais je sais ce que je vends à mes clients. »\n" +
                        "\n" +
                        "La résonance est palpable auprès des fournisseurs installés à proximité de son entreprise. Les producteurs forestiers expédient directement leur matière première à la scierie de la Côte-du-Sud, d’où le président de Matériaux Direct se fournit. Par la suite, ce dernier revend cette matière en provenance de la région du Centre-du-Québec. En doublant la superficie de ses infrastructures en 2020, il devient ensuite capable de préparer et de recevoir le bois traité. Ainsi, Maxime Bossinotte assure dès lors la traçabilité de son produit, et ce, de A à Z.\n" +
                        "\n" +
                        "Le propriétaire de Matériaux Direct saisit ensuite l’occasion de stimuler le périmètre de La Pocatière. Cette nouvelle façon de procéder trouve bien des avantages, puisqu’elle orientera ses choix dans une portée écologique, économique et sociale. Une gestion proactive qui va valoir son pesant d’or.\n" +
                        "\n" +
                        "À l’automne 2020, il décide alors de faire traiter son bois dans une usine à Montmagny. Et en s’approvisionnant dans les scieries dans la région de Sainte-Perpétue, la chaine devient plus économique en termes de temps et d’argent. « Présentement, mes fournisseurs se trouvent dans un rayon de 45 minutes de transport au maximum. »\n" +
                        "\n" +
                        "Face à cette méthode avantageuse, il ne dissimule pas son optimisme. Si la pandémie a marqué le domaine de la construction au fer rouge, il a su viser juste. Il bénéficie aujourd’hui d’une reconnaissance qui l’incite à conserver la même trajectoire – cent pour cent locale.",
                "08/08/22"));
        list.add(new News("news_pont",
                "Aluminium : un choix judicieux dans le domaine des ponts et des passerelles",
                "Plus de 70 ans après la construction du premier pont routier en aluminium au Québec, ce métal peine toujours à s’imposer au sein de ces grandes structures. Or, la situation serait sur le point de changer.\n",
                "« D’ici cinq ans, nous devrions être capables de faire des ponts en aluminium comme on fait des ponts en acier-béton », prédit le responsable du chantier Infrastructures et ouvrages d’art chez AluQuébec, Mario Fafard. Ces dernières années, cet ingénieur civil s’est donné comme mission de convaincre les donneurs d’ouvrage de favoriser ce matériau, dont les avantages sont majeurs : léger, esthétiquement beau, durable et résistant à la corrosion atmosphérique.\n" +
                        "\n" +
                        "« Je dis toujours à la blague que si le pont de Québec était en aluminium, on n’en parlerait pas. L’aluminium, ça ne rouille pas et ça ne se corrode pas », illustre-t-il, en faisant référence aux nombreux travaux effectués sur ce pont depuis sa construction au début des années 1900. Selon l’Association de l’aluminium du Canada, l’importante détérioration de platelages de ponts en béton armé causée par l’utilisation du sel de déglaçage est une autre raison qui favorise l’utilisation de ce matériau.\n" +
                        "\n" +
                        "Mario Fafard, Responsable du chantier Infrastructures et ouvrages d’art chez AluQuébec. Crédit : Gracieuseté\n" +
                        "\n" +
                        "En plus de ses qualités évidentes, ce métal ne manque vraiment pas au Québec. La province compte neuf alumineries, dont la capacité de production d’aluminium primaire totalise 2,8 tonnes, soit 90 pour cent (%) de la production canadienne. Le pays se classe au quatrième rang mondial à ce chapitre.\n" +
                        "\n" +
                        "Malgré tout, seulement deux ponts sont construits avec ce matériau au Québec : celui d’Arvida au Saguenay, construit en 1950, et celui de Saint-Ambroise, situé dans la même région, dont le platelage d’aluminium a été ajouté en 2015.\n" +
                        "\n" +
                        "Pourquoi donc l’aluminium est-il aussi peu utilisé dans la construction des ponts par rapport au béton, à l’acier et au bois ? Mario Fafard avance deux raisons. Premièrement, le manque de connaissances des ingénieurs quant à ce matériau représente un frein majeur à son utilisation. À l’heure actuelle, les programmes de formation offrent peu d’enseignement sur le sujet. Deuxièmement, le ministère des Transports du Québec (MTQ) est encore réticent à l’utiliser. « Le MTQ est prudent. Il veut s’assurer qu’il n’y ait pas de problème avec l’aluminium dans le futur », souligne l’ingénieur civil.\n" +
                        "\n" +
                        "Toutefois, cette réserve n’est pas uniquement motivée par des questions de sécurité. Le MTQ, qui applique sa politique du plus bas cout, hésite à favoriser ce métal puisque son prix initial est plus élevé que l’acier ou le béton. Or, sur une durée de vie de 75 ans, l’aluminium serait moins dispendieux puisqu’il demande moins d’entretien.\n" +
                        "\n" +
                        "Une étude de l’Université Laval a comparé les couts du cycle de vie d’un pont non composite aluminium-acier et d’un pont composite béton-acier. Les résultats ont démontré que le prix initial d’un platelage en aluminium est deux fois plus élevé que celui d’une dalle de béton mais que, globalement, il est quatre fois moins élevé sur l’ensemble du cycle de vie.\n" +
                        "\n" +
                        "« Tant et aussi longtemps qu’il n’y aura pas un changement de mentalité au Conseil du trésor et que l’on ne calculera pas les sommes investies sur la durée de vie d’un pont, l’aluminium sera défavorisé », soutient-il.\n" +
                        "\n" +
                        "Il semble cependant que ce changement de culture soit en train de s’opérer. D’abord, Québec utilise de plus en plus l’aluminium dans le secteur du génie civil, que ce soit pour ses panneaux de signalisation ou pour ses lampadaires. Ensuite, d’ici 2022, le MTQ compte investir environ cinq millions de dollars dans un projet de recherche qui vise à développer un platelage en aluminium testé en laboratoire à l’Université Laval. Ce platelage sera installé sur un pont dont la construction est prévue en 2022 dans le secteur de Montmorency à Québec. Ensuite, cette structure sera examinée et analysée pendant quelques années pour comprendre comment elle se comporte.\n" +
                        "\n" +
                        "AluQuébec voit une belle occasion de développer son expertise qui va ensuite permettre au Québec d’exporter son produit notamment aux États-Unis ou ailleurs au Canada. Au cours des prochaines décennies, plus de mille ponts devront être remplacés ou réparés seulement dans le nord-est de l’Amérique du Nord, offrant une belle occasion d’affaires aux producteurs d’aluminium.\n" +
                        "\n" +
                        "Les passerelles ouvrent la voie\n" +
                        "Les passerelles, contrairement aux ponts, sont plus fréquemment constituées d’aluminium. Crédit : MAADI Group\n" +
                        "\n" +
                        "Si les ponts en aluminium sont peu nombreux au Québec, ce n’est pas le cas des passerelles. Mario Fafard en a dénombré 44 à travers la province, dont certaines sont situées dans des ports ou au sein d’installations industrielles, où l’on valorise la haute résistance à la corrosion qu’offre ce matériau. De plus, ces passerelles ont la caractéristique d’être légères, pouvant donc être construites en usine pour être ensuite transportées. Ce n’est pas toujours le cas pour les passerelles en acier.\n" +
                        "\n" +
                        "Selon l’ingénieur, il y a moins de réticence à construire des passerelles principalement parce que ce n’est pas le MTQ qui est le donneur d’ouvrage. Par exemple, celle du canal de Lachine à Montréal appartient à Parcs Canada, alors que la structure à Chandler en Gaspésie est la propriété de la Ville. Comme pour les ponts, les passerelles fabriquées avec ce matériau demandent très peu de travaux de réparation. La plus vieille, construite en 1985, est impeccable et n’a eu à subir aucun entretien. « Une entreprise qui ne construirait pas une passerelle en aluminium aujourd’hui, je ne la comprends pas », lance l’ingénieur civil.\n" +
                        "\n" +
                        "Celui-ci rêve, un jour, de dire la même chose pour les ponts. Entretemps, AluQuébec veillera à ce qu’il y ait plus d’enseignement sur ce métal dans les formations de génie civil et continuera à faire valoir les mérites de ce matériau.",
                "05/08/22"));
        list.add(new News("news_lego",
                "LEGO déconstruit le concept d’espace de travail",
                "Après cinq ans de planification et de construction, le groupe LEGO a inauguré, au printemps 2022, le campus de son siège social de Billund, au Danemark.\n",
                "Facilement reconnaissable aux deux grosses briques jaunes qui ornent son toit, le campus LEGO conçu par la firme d’architecture C.F. Møller, est composé de huit bâtiments reliés entre eux totalisant une superficie de 54 000 mètres carrés. Si l’extérieur offre plusieurs clins d’œil aux fameuses briques (reproductions géantes intercalées aux fenêtres, revêtement et puits de lumière ronds…), le design intérieur est tout aussi caractéristique de la marque.\n" +
                        "\n" +
                        "Les espaces de bureaux flexibles se rassemblent autour d’un atrium dont les caractéristiques et les couleurs sont inspirées des produits du groupe. L’ensemble évite délibérément l’uniformité au profit de la diversité et du divertissement, afin de refléter le jeu de construction danois. Comme l’explique Niels B. Christiansen, PDG du groupe LEGO, « Notre mission est d’inspirer les enfants, il est donc important que nous fournissions un lieu de travail dynamique et ludique qui permette aux employés de créer des produits originaux », déclare Niels B. Christiansen, PDG du groupe LEGO.\n" +
                        "\n" +
                        "Le site compte 2000 employés de la région, mais également de nombreux collègues des quatre coins du monde de passage en voyage d’affaires. Un hôtel de deux étages a ainsi été aménagé dans la partie nord-est du complexe pour loger ces derniers. \n" +
                        "\n" +
                        "Pendant deux ans, Anneke Beerkens, anthropologue organisationnelle principale du groupe LEGO, a travaillé avec des centaines d’employés du monde entier pour concevoir le lieu de travail idéal. « De la même manière que vous construisez avec des briques LEGO, nous avons pris des éléments que nos employés aiment et les avons réunis pour créer quelque chose d’unique. »\n" +
                        "\n" +
                        "De ce processus d’idéation collaboratif est né un environnement de travail comprenant non seulement des bureaux, espaces collaboratifs, ateliers, locaux techniques et entrepôts, mais également des espaces de détente et de divertissement, concentrés au rez-de-chaussée, dans ce qui a été nommé la « People House ». Celle-ci est composée notamment d’un studio de fitness, d’un espace d’atelier créatif, d’un cinéma, d’une cuisine commune ainsi que d’une clinique santé. Ces installations sont ouvertes non seulement aux employés du Groupe, mais également à leurs familles.\n" +
                        "\n" +
                        "« Le campus a été conçu comme une miniville, composée de maisons qui forment des ruelles et des places, où les lieux de travail, les quartiers et les zones d’activité fusionnent avec le paysage environnant composé de toits verts, de terrasses et de jardins », explique Klaus Toustrup, architecte, partenaire C.F. Møller Architects.\n" +
                        "\n" +
                        "L’intégration du bâtiment dans un grand parc, accessible au public, crée non seulement des espaces de rencontre, de promenade et de jeux extérieurs, mais également un paysage de verdure à contempler de l’intérieur. Les employés qui souhaitent prendre une bouffée d’air frais ont également accès à des toits végétalisés comprenant une serre et un parcours de minigolf.\n" +
                        "\n" +
                        "Le développement durable est au cœur de la conception du nouveau Campus. En plus de ses nombreux espaces verts qui contribuent au bien-être des employés tout en améliorant la biodiversité, le projet inclut un système de collecte des eaux de pluie (redistribuées dans des bassins et des lacs), des panneaux solaires (installés sur le toit d’un stationnement voisin) et l’emploi de matériaux durables. Des initiatives qui ont valu au projet une certification LEED or.",
                "05/08/22"));
        list.add(new News("news_hotel",
                "Un complexe hôtelier conçu à partir de plastique recyclé",
                "La firme d’architecture britannique Margot Krasojević Architecture a présenté une proposition audacieuse pour résoudre les problèmes de pollution de l’océan Indien, soit la construction d’un complexe flottant de luxe au large des côtes australiennes, fabriqué à partir de déchets.\n",
                "Facilement reconnaissable aux deux grosses briques jaunes qui ornent son toit, le campus LEGO conçu par la firme d’architecture C.F. Møller, est composé de huit bâtiments reliés entre eux totalisant une superficie de 54 000 mètres carrés. Si l’extérieur offre plusieurs clins d’œil aux fameuses briques (reproductions géantes intercalées aux fenêtres, revêtement et puits de lumière ronds…), le design intérieur est tout aussi caractéristique de la marque.\n" +
                        "\n" +
                        "Les espaces de bureaux flexibles se rassemblent autour d’un atrium dont les caractéristiques et les couleurs sont inspirées des produits du groupe. L’ensemble évite délibérément l’uniformité au profit de la diversité et du divertissement, afin de refléter le jeu de construction danois. Comme l’explique Niels B. Christiansen, PDG du groupe LEGO, « Notre mission est d’inspirer les enfants, il est donc important que nous fournissions un lieu de travail dynamique et ludique qui permette aux employés de créer des produits originaux », déclare Niels B. Christiansen, PDG du groupe LEGO.\n" +
                        "\n" +
                        "Conçu comme une miniville\n" +
                        "Le site compte 2000 employés de la région, mais également de nombreux collègues des quatre coins du monde de passage en voyage d’affaires. Un hôtel de deux étages a ainsi été aménagé dans la partie nord-est du complexe pour loger ces derniers. \n" +
                        "\n" +
                        "Pendant deux ans, Anneke Beerkens, anthropologue organisationnelle principale du groupe LEGO, a travaillé avec des centaines d’employés du monde entier pour concevoir le lieu de travail idéal. « De la même manière que vous construisez avec des briques LEGO, nous avons pris des éléments que nos employés aiment et les avons réunis pour créer quelque chose d’unique. »\n" +
                        "\n" +
                        "De ce processus d’idéation collaboratif est né un environnement de travail comprenant non seulement des bureaux, espaces collaboratifs, ateliers, locaux techniques et entrepôts, mais également des espaces de détente et de divertissement, concentrés au rez-de-chaussée, dans ce qui a été nommé la « People House ». Celle-ci est composée notamment d’un studio de fitness, d’un espace d’atelier créatif, d’un cinéma, d’une cuisine commune ainsi que d’une clinique santé. Ces installations sont ouvertes non seulement aux employés du Groupe, mais également à leurs familles.\n" +
                        "\n" +
                        "« Le campus a été conçu comme une miniville, composée de maisons qui forment des ruelles et des places, où les lieux de travail, les quartiers et les zones d’activité fusionnent avec le paysage environnant composé de toits verts, de terrasses et de jardins », explique Klaus Toustrup, architecte, partenaire C.F. Møller Architects.\n" +
                        "\n" +
                        "L’intégration du bâtiment dans un grand parc, accessible au public, crée non seulement des espaces de rencontre, de promenade et de jeux extérieurs, mais également un paysage de verdure à contempler de l’intérieur. Les employés qui souhaitent prendre une bouffée d’air frais ont également accès à des toits végétalisés comprenant une serre et un parcours de minigolf.\n" +
                        "\n" +
                        "Le développement durable est au cœur de la conception du nouveau Campus. En plus de ses nombreux espaces verts qui contribuent au bien-être des employés tout en améliorant la biodiversité, le projet inclut un système de collecte des eaux de pluie (redistribuées dans des bassins et des lacs), des panneaux solaires (installés sur le toit d’un stationnement voisin) et l’emploi de matériaux durables. Des initiatives qui ont valu au projet une certification LEED or.",
                "03/08/22"));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        NewsFeedAdapter adapter = new NewsFeedAdapter(list, homeActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeActivity));
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);

        init();

        return view;
    }

    @Override
    public void onItemClick(News news) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        bundle.putString("image", news.getImage());
        bundle.putString("titre", news.getTitre());
        bundle.putString("intro", news.getIntro());
        bundle.putString("article", news.getArticle());
        bundle.putString("date", news.getDate());
        intent.putExtras(bundle);
        mCallback.onButtonClicked(view, bundle);
    }

    private void init() {
        for (int image : images) ImagesArray.add(image);
        NUM_PAGES =images.length;

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }


    private void createCallbackToParentActivity(){
        try {
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e + " must implement OnButtonClickedListener");
        }
    }
}