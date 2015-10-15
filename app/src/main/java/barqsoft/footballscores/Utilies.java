package barqsoft.footballscores;



/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

// *** use of string values fixed
    public static String getLeague(int league_num)
    {
        switch (league_num)
        {
            case SERIE_A : return App.getContext().getString(R.string.seriaa);
            case PREMIER_LEGAUE : return App.getContext().getString(R.string.premierleague);
            case CHAMPIONS_LEAGUE : return App.getContext().getString(R.string.champions_league);
            case PRIMERA_DIVISION : return App.getContext().getString(R.string.primeradivison);
            case BUNDESLIGA : return App.getContext().getString(R.string.bundesliga);
            default: return App.getContext().getString(R.string.unkonw_league);
        }
    }
    public static String getMatchDay(int match_day, int league_num)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return App.getContext().getString(R.string.group_stage_text) +", " + App.getContext().getString(R.string.matchday_text) + " : 6";
            }
            else if(match_day == 7 || match_day == 8)
            {
                return App.getContext().getString(R.string.first_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return App.getContext().getString(R.string.quarter_final);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return App.getContext().getString(R.string.semi_final);
            }
            else
            {
                return App.getContext().getString(R.string.final_text);
            }
        }
        else
        {
            return App.getContext().getString(R.string.matchday_text) + " : " + match_day;
        }
    }

    public static String getScores( int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return App.getContext().getString(home_goals) + " - " + App.getContext().getString(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        if (teamname.equals(App.getContext().getString(R.string.team_arsenal))) {
            return R.drawable.arsenal;
        } else if (teamname.equals(App.getContext().getString(R.string.team_manchesterUnited))) {
            return R.drawable.manchester_united;
        } else if (teamname.equals(App.getContext().getString(R.string.team_swansea))) {
            return R.drawable.swansea_city_afc;
        } else if (teamname.equals(App.getContext().getString(R.string.team_leicester))) {
            return R.drawable.leicester_city_fc_hd_logo;
        } else if (teamname.equals(App.getContext().getString(R.string.team_everton))) {
            return R.drawable.everton_fc_logo1;
        } else if (teamname.equals(App.getContext().getString(R.string.team_westham))) {
            return R.drawable.west_ham;
        } else if (teamname.equals(App.getContext().getString(R.string.team_tottenham))) {
            return R.drawable.tottenham_hotspur;
        } else if (teamname.equals(App.getContext().getString(R.string.team_westbromwich))) {
            return R.drawable.west_bromwich_albion_hd_logo;
        } else if (teamname.equals(App.getContext().getString(R.string.team_sunderland))) {
            return R.drawable.sunderland;
        } else if (teamname.equals(App.getContext().getString(R.string.team_stoke))) {
            return R.drawable.stoke_city;
        } else {
            return R.drawable.no_icon;
        }
    }
}
