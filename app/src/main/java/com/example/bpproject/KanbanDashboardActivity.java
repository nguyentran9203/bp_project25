package com.example.bpproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KanbanDashboardActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_DETAIL = 1;

    RecyclerView todoRecyclerView, inProgressRecyclerView, doneRecyclerView;
    KanbanAdapter todoAdapter, inProgressAdapter, doneAdapter;
    List<Commission> todoList, inProgressList, doneList;

    // Original lists to preserve full data for filtering
    List<Commission> allTodoList, allInProgressList, allDoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_dashboard);

        todoRecyclerView = findViewById(R.id.todoRecyclerView);
        inProgressRecyclerView = findViewById(R.id.inProgressRecyclerView);
        doneRecyclerView = findViewById(R.id.doneRecyclerView);


        Button buttonFilterService = findViewById(R.id.buttonFilterService);

        todoList = new ArrayList<>();
        inProgressList = new ArrayList<>();
        doneList = new ArrayList<>();

        // Dummy data
        todoList.add(new Commission("Alice", "2024-05-01", 0, "todo", "Character Design"));
        todoList.add(new Commission("David", "2024-05-10", 25, "todo", "Character Illustration"));

        inProgressList.add(new Commission("Bob", "2024-05-02", 50, "inProgress", "Character Illustration"));
        inProgressList.add(new Commission("Eve", "2024-05-11", 75, "inProgress", "Character Illustration"));

        doneList.add(new Commission("Charlie", "2024-05-03", 100, "done", "Character Design"));
        doneList.add(new Commission("Frank", "2024-05-12", 100, "done", "Character Illustration"));

        // Keep full lists for filtering
        allTodoList = new ArrayList<>(todoList);
        allInProgressList = new ArrayList<>(inProgressList);
        allDoneList = new ArrayList<>(doneList);

        // Adapters
        todoAdapter = new KanbanAdapter(todoList, position -> openDetailActivity("todo", position));
        inProgressAdapter = new KanbanAdapter(inProgressList, position -> openDetailActivity("inProgress", position));
        doneAdapter = new KanbanAdapter(doneList, position -> openDetailActivity("done", position));

        // Setup RecyclerViews
        setupRecyclerView(todoRecyclerView, todoAdapter);
        setupRecyclerView(inProgressRecyclerView, inProgressAdapter);
        setupRecyclerView(doneRecyclerView, doneAdapter);

        buttonFilterService = findViewById(R.id.buttonFilterService);

        buttonFilterService.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(this, view);
            popup.getMenuInflater().inflate(R.menu.menu_filter_service, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.filter_character_design) {
                    filterByService("Character Design");
                    return true;
                } else if (id == R.id.filter_character_illustration) {
                    filterByService("Character Illustration");
                    return true;
                } else if (id == R.id.filter_all) {
                    showAll();
                    return true;
                }
                return false;
            });

            popup.show();
        });

    }

    private void setupRecyclerView(RecyclerView recyclerView, KanbanAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) {
                int from = vh.getAdapterPosition();
                int to = target.getAdapterPosition();
                adapter.moveItem(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder vh, int direction) {
                // no swipe
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void openDetailActivity(String listName, int position) {
        Commission commission = null;

        switch (listName) {
            case "todo":
                commission = todoList.get(position);
                break;
            case "inProgress":
                commission = inProgressList.get(position);
                break;
            case "done":
                commission = doneList.get(position);
                break;
        }

        if (commission != null) {
            Intent intent = new Intent(this, KanbanCardDetailActivity.class);
            intent.putExtra("clientName", commission.clientName);
            intent.putExtra("date", commission.date);
            intent.putExtra("progress", commission.progressPercent);
            intent.putExtra("listName", listName);
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_CODE_DETAIL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_DETAIL && resultCode == RESULT_OK && data != null) {
            String listName = data.getStringExtra("listName");
            int position = data.getIntExtra("position", -1);
            int updatedProgress = data.getIntExtra("progress", 0);

            if (position == -1 || listName == null) return;

            Commission commission = null;
            KanbanAdapter adapter = null;

            switch (listName) {
                case "todo":
                    if (position < todoList.size()) {
                        commission = todoList.get(position);
                        adapter = todoAdapter;
                    }
                    break;
                case "inProgress":
                    if (position < inProgressList.size()) {
                        commission = inProgressList.get(position);
                        adapter = inProgressAdapter;
                    }
                    break;
                case "done":
                    if (position < doneList.size()) {
                        commission = doneList.get(position);
                        adapter = doneAdapter;
                    }
                    break;
            }

            if (commission != null) {
                commission.progressPercent = updatedProgress;
                if (adapter != null) adapter.notifyItemChanged(position);
            }
        }
    }

    private void filterByService(String serviceName) {
        List<Commission> filteredTodo = new ArrayList<>();
        List<Commission> filteredInProgress = new ArrayList<>();
        List<Commission> filteredDone = new ArrayList<>();

        for (Commission c : allTodoList) {
            if (c.serviceName.equals(serviceName)) filteredTodo.add(c);
        }
        for (Commission c : allInProgressList) {
            if (c.serviceName.equals(serviceName)) filteredInProgress.add(c);
        }
        for (Commission c : allDoneList) {
            if (c.serviceName.equals(serviceName)) filteredDone.add(c);
        }

        todoList.clear();
        inProgressList.clear();
        doneList.clear();

        todoList.addAll(filteredTodo);
        inProgressList.addAll(filteredInProgress);
        doneList.addAll(filteredDone);

        todoAdapter.notifyDataSetChanged();
        inProgressAdapter.notifyDataSetChanged();
        doneAdapter.notifyDataSetChanged();
    }

    private void showAll() {
        todoList.clear();
        inProgressList.clear();
        doneList.clear();

        todoList.addAll(allTodoList);
        inProgressList.addAll(allInProgressList);
        doneList.addAll(allDoneList);

        todoAdapter.notifyDataSetChanged();
        inProgressAdapter.notifyDataSetChanged();
        doneAdapter.notifyDataSetChanged();
    }

    public class Commission {
        public String clientName;
        public String date;
        public int progressPercent;
        public String status;
        public String serviceName;

        public Commission(String clientName, String date, int progressPercent, String status, String serviceName) {
            this.clientName = clientName;
            this.date = date;
            this.progressPercent = progressPercent;
            this.status = status;
            this.serviceName = serviceName;
        }
    }
}
