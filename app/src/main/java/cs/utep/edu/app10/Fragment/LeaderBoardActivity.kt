package cs.utep.edu.app10.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cs.utep.edu.app10.Player
import cs.utep.edu.app10.PlayerAdapter
import cs.utep.edu.app10.R
import cs.utep.edu.app10.loadImg

import kotlinx.android.synthetic.main.activity_leader_board.*
import kotlinx.android.synthetic.main.content_scrolling.*

class LeaderBoardActivity : Fragment() {
    private var recycler_view: RecyclerView? = null
    private lateinit var championOne : ImageView
    private lateinit var championTwo : ImageView
    private lateinit var championThree : ImageView
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_leader_board,container,false)
        recycler_view = view.findViewById(R.id.recycler_view)
        recycler_view?.layoutManager = LinearLayoutManager(activity)
        recycler_view?.adapter = PlayerAdapter()
        championOne = view.findViewById(R.id.iv_champion1)
        championTwo = view.findViewById(R.id.iv_champion2)
        championThree = view.findViewById(R.id.iv_champion3)


        //setupRecyclerView()
        loadPlayers()
        return view
    }

//    private fun setupRecyclerView() {
//        recycler_view.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = PlayerAdapter()
//            setHasFixedSize(true)
//        }
//    }

    private fun loadPlayers() {
        //val db = FirebaseFirestore.getInstance()
//        db.collection("players")
//                .orderBy("score", Query.Direction.DESCENDING)
//                .addSnapshotListener({ snapshots, error ->
//                    if (error != null) {
//                        Log.d("TAG", error.message)
//                        return@addSnapshotListener
//                    }
//
//                    val players = snapshots.map{
//                        it.toObject(Player::class.java)
//                    }
//                    val champions = players.take(3)
//                    showPlayersPosition(players)
//                    showChampions(champions)
//                })
        var players = MutableList(0){Player()}
        val p1 = Player(name = "Natalia",nationality = "USA",score=150,photo = "https://www.utep.edu/cs/people/FacStaff/NataliaVillanueva.jpg")
        val p2 = Player(name = "Kelvin",nationality = "USA",score=120,photo = "https://expertise.utep.edu/expertiseFiles/profileImages/rcheu_copy.jpg")
        val p3 = Player(name = "Daniel",nationality = "USA",score=90,photo = "https://www.utep.edu/cs/people/TAs/DanielOrnelas200x300.png")
        val p4 = Player(name = "Jorge",nationality = "USA",score=55,photo = "https://media.licdn.com/dms/image/C5603AQE7UxNYmOJLnw/profile-displayphoto-shrink_800_800/0?e=1562198400&v=beta&t=-EyM7Z4_joLdidkhYVY7v3bkDP-SGXyWRywb13fbfP8")
        val p5 = Player(name = "Gina",nationality = "USA",score=24,photo = "https://www.utep.edu/liberalarts/women-studies/faculty/LiberalArts-Nunez-Mchiri-G.jpg")

        players.add(p1)
        players.add(p2)
        players.add(p3)
        players.add(p4)
        players.add(p5)


        val champions = players

        showPlayersPosition(players)
        showChampions(champions)

    }

    private fun showPlayersPosition(players: List<Player>) {
        val adapter = recycler_view?.adapter as PlayerAdapter
        adapter.addPlayers(players)
    }

    private fun showChampions(championPlayers: List<Player>) {
        championOne.loadImg(championPlayers[0].photo)
        championTwo.loadImg(championPlayers[1].photo)
        championThree.loadImg(championPlayers[2].photo)

    }
}