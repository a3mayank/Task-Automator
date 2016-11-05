package com.mayankattri.mc_project_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class GeofencingActivityFragment extends Fragment implements AddGeofenceFragment.AddGeofenceFragmentListener {

    public GeofencingActivityFragment() {
    }

    private ViewHolder viewHolder;
    private AllGeofencesAdapter allGeofencesAdapter;

    private ViewHolder getViewHolder() {
        return viewHolder;
    }

    private GeofenceController.GeofenceControllerListener geofenceControllerListener = new GeofenceController.GeofenceControllerListener() {
        @Override
        public void onGeofencesUpdated() {
            refresh();
        }

        @Override
        public void onError() {
            showErrorToast();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_geofencing, container, false);
        viewHolder = new ViewHolder();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViewHolder().populate(view);

        viewHolder.geofenceRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        viewHolder.geofenceRecyclerView.setLayoutManager(layoutManager);

        allGeofencesAdapter = new AllGeofencesAdapter(GeofenceController.getInstance().getNamedGeofences());
        viewHolder.geofenceRecyclerView.setAdapter(allGeofencesAdapter);
        allGeofencesAdapter.setListener(new AllGeofencesAdapter.AllGeofencesAdapterListener() {
            @Override
            public void onDeleteTapped(NamedGeofence namedGeofence) {
                List<NamedGeofence> namedGeofences = new ArrayList<>();
                namedGeofences.add(namedGeofence);
                GeofenceController.getInstance().removeGeofences(namedGeofences, geofenceControllerListener);
            }
        });

        viewHolder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGeofenceFragment dialogFragment = new AddGeofenceFragment();
                dialogFragment.setListener(GeofencingActivityFragment.this);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "AddGeofenceFragment");
            }
        });

        viewHolder.actionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MapsActivity.class);
                myIntent.putExtra("key", "map"); //Optional parameters
                startActivity(myIntent);
            }
        });

        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.AreYouSure)
                    .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GeofenceController.getInstance().removeAllGeofences(geofenceControllerListener);
                        }
                    })
                    .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    })
                    .create()
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        allGeofencesAdapter.notifyDataSetChanged();

        if (allGeofencesAdapter.getItemCount() > 0) {
            getViewHolder().emptyState.setVisibility(View.INVISIBLE);
        } else {
            getViewHolder().emptyState.setVisibility(View.VISIBLE);

        }

        getActivity().invalidateOptionsMenu();

    }

    private void showErrorToast() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.Toast_Error), Toast.LENGTH_SHORT).show();
    }

    // endregion

    // region AddGeofenceFragmentListener

    @Override
    public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog, NamedGeofence geofence) {
        GeofenceController.getInstance().addGeofence(geofence, geofenceControllerListener);
    }

    @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog) {
        // Do nothing
    }

    // endregion

    // region Inner classes

    static class ViewHolder {
        ViewGroup container;
        ViewGroup emptyState;
        RecyclerView geofenceRecyclerView;
        ActionButton actionButton, actionButton2;

        public void populate(View v) {
            container = (ViewGroup) v.findViewById(R.id.fragment_all_geofences_container);
            emptyState = (ViewGroup) v.findViewById(R.id.fragment_all_geofences_emptyState);
            geofenceRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_all_geofences_geofenceRecyclerView);
            actionButton = (ActionButton) v.findViewById(R.id.fragment_all_geofences_actionButton);
            actionButton2 = (ActionButton) v.findViewById(R.id.fragment_all_geofences_actionButton2);

            actionButton.setImageResource(R.drawable.fab_plus_icon);
            actionButton2.setImageResource(R.drawable.fab_plus_icon);
        }
    }
}
