package ru.altimin.hat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.terlici.dragndroplist.DragNDropCursorAdapter;
import com.terlici.dragndroplist.DragNDropListView;
import com.terlici.dragndroplist.DragNDropSimpleAdapter;
import ru.altimin.hat.R;
import ru.altimin.hat.game.GameSettings;
import ru.altimin.hat.game.Player;
import ru.altimin.hat.game.PlayersOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: altimin
 * Date: 19/05/13
 * Time: 14:47
 */
public class PlayersOrderingActivity extends Activity {
    private GameSettings gameSettings;
    private List<Map<String, Object>> data;
    private DragNDropSimpleAdapter adapter;

    private final String DEBUG_TAG = "PlayersOrderingActivity";

    private List<Map<String, Object>> buildData(GameSettings settings) {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Player player: settings.getPlayers()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("playerName", player.getName());
            item.put("player", player);
            result.add(item);
        }
        return result;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseplayersorderlayout);
        gameSettings = (GameSettings) getIntent().getSerializableExtra("settings");

        final Button orderingOkButton = (Button) findViewById(R.id.orderingokbutton);
        orderingOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        DragNDropListView list = (DragNDropListView) findViewById(R.id.playersorderlist);

        String[] from = { "playerName" };
        int[] to = { R.id.text };

        data = buildData(gameSettings);

        adapter = new DragNDropSimpleAdapter(this, data, R.layout.rowlayout, from, to, R.id.text);

        list.setDragNDropAdapter(adapter);
    }

    public void startGame() {
        List<Player> players = new ArrayList<Player>();
        int positions = data.size();
        for (int i = 0; i < positions; i ++) {
            Player player = (Player) ((Map<String, Object>) adapter.getItem(i)).get("player");
            Log.d(DEBUG_TAG, player.getName());
            players.add(player);
        }
        PlayersOrder order = new PlayersOrder(players);

        Intent startGameIntent = new Intent(PlayersOrderingActivity.this, GameActivity.class);
        startGameIntent.putExtra("settings", gameSettings);
        startGameIntent.putExtra("order", order);
        PlayersOrderingActivity.this.startActivity(startGameIntent);

        finish();
    }
}
