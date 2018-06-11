package com.example.conferenceapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityFoodGuide;
import com.example.conferenceapp.activities.ActivityPaperDetails;
import com.example.conferenceapp.activities.NavBarActivity;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.utils.PaperCSVParser;

import java.io.Serializable;

public class FragmentDaySchedule extends Fragment implements Serializable{

    public static final String ARG_PAGE = "ARG_PAGE";
    public String breakfast[] = {"10:30AM", "BREAKFAST", "10:50AM"};
    public String lunch[] = {"1:00PM", "LUNCH", "2:00PM"};
    private int mPage;

    public static FragmentDaySchedule newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentDaySchedule fragment = new FragmentDaySchedule();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_schedule, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            PaperCSVParser.parseCSV(view.getContext());
        } catch (Exception e) {
        }

        LinearLayout root = view.findViewById(R.id.daySchedule);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        if (mPage == 1) {
            View bFast = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView start = bFast.findViewById(R.id.breakStartTime);
            TextView desc = bFast.findViewById(R.id.breakDescTextView);
            TextView end = bFast.findViewById(R.id.breakEndTime);

            start.setText(breakfast[0]);
            desc.setText(breakfast[1]);
            end.setText(breakfast[2]);
            root.addView(bFast);
            bFast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ActivityFoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
                View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper_view.findViewById(R.id.paperName);
                TextView papervenue = paper_view.findViewById(R.id.paperVenue);
                TextView paperend = paper_view.findViewById(R.id.paperTimings);
                final TextView addPaper = paper_view.findViewById(R.id.add);
                final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
                final Paper paper = PaperCSVParser.papers.get(i);
                if (paper.getTime().getDate().equals("2 Dec 18") && Integer.parseInt(paper.getTime().getStartTime().split(":")[0]) <= 12) {
                    paperstart.setText(paper.getTitle());
                    papervenue.setText(paper.getVenue());
                    paperend.setText(paper.getTime().displayTime());
                    root.addView(paper_view);
                    paper_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                            intent.putExtra("Paper", paper);
                            startActivity(intent);
                        }
                    });
                    paperAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_green_24dp));
                            addPaper.setTextColor(Color.parseColor("#0F9D57"));
                        }
                    });
                }
            }

            View lunch = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView lunchStart = lunch.findViewById(R.id.breakStartTime);
            TextView lunchDesc = lunch.findViewById(R.id.breakDescTextView);
            TextView lunchEnd = lunch.findViewById(R.id.breakEndTime);

            lunchStart.setText(this.lunch[0]);
            lunchDesc.setText(this.lunch[1]);
            lunchEnd.setText(this.lunch[2]);
            root.addView(lunch);
            lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ActivityFoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
                View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper_view.findViewById(R.id.paperName);
                TextView papervenue = paper_view.findViewById(R.id.paperVenue);
                TextView paperend = paper_view.findViewById(R.id.paperTimings);
                final TextView addPaper = paper_view.findViewById(R.id.add);
                final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
                final Paper paper = PaperCSVParser.papers.get(i);
                if (paper.getTime().getDate().equals("2 Dec 18") && Integer.parseInt(paper.getTime().getStartTime().split(":")[0]) >=1 && Integer.parseInt(paper.getTime().getStartTime().split(":")[0]) <=5 ) {
                    paperstart.setText(paper.getTitle());
                    papervenue.setText(paper.getVenue());
                    paperend.setText(paper.getTime().displayTime());
                    root.addView(paper_view);
                    paper_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                            intent.putExtra("Paper", paper);
                            startActivity(intent);
                        }
                    });

                    paperAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_green_24dp));
                            addPaper.setTextColor(Color.parseColor("#0F9D57"));
                        }
                    });
                }
            }
        } else if (mPage == 2) {
            View bFast = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView start = bFast.findViewById(R.id.breakStartTime);
            TextView desc = bFast.findViewById(R.id.breakDescTextView);
            TextView end = bFast.findViewById(R.id.breakEndTime);

            start.setText(breakfast[0]);
            desc.setText(breakfast[1]);
            end.setText(breakfast[2]);
            root.addView(bFast);
            bFast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ActivityFoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
                View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper_view.findViewById(R.id.paperName);
                TextView papervenue = paper_view.findViewById(R.id.paperVenue);
                TextView paperend = paper_view.findViewById(R.id.paperTimings);
                final TextView addPaper = paper_view.findViewById(R.id.add);
                final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
                final Paper paper = PaperCSVParser.papers.get(i);
                if (paper.getTime().getDate().equals("3 Dec 18") && Integer.parseInt(paper.getTime().getStartTime().split(":")[0]) <= 12) {
                    paperstart.setText(paper.getTitle());
                    papervenue.setText(paper.getVenue());
                    paperend.setText(paper.getTime().displayTime());
                    root.addView(paper_view);
                    paper_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                            intent.putExtra("Paper", paper);
                            startActivity(intent);
                        }
                    });

                    paperAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_green_24dp));
                            addPaper.setTextColor(Color.parseColor("#0F9D57"));
                        }
                    });
                }
            }

            View lunch = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView lunchStart = lunch.findViewById(R.id.breakStartTime);
            TextView lunchDesc = lunch.findViewById(R.id.breakDescTextView);
            TextView lunchEnd = lunch.findViewById(R.id.breakEndTime);

            lunchStart.setText(this.lunch[0]);
            lunchDesc.setText(this.lunch[1]);
            lunchEnd.setText(this.lunch[2]);
            root.addView(lunch);
            lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ActivityFoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
                View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper_view.findViewById(R.id.paperName);
                TextView papervenue = paper_view.findViewById(R.id.paperVenue);
                TextView paperend = paper_view.findViewById(R.id.paperTimings);
                final TextView addPaper = paper_view.findViewById(R.id.add);
                final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
                final Paper paper = PaperCSVParser.papers.get(i);
                if (paper.getTime().getDate().equals("3 Dec 18") && Integer.parseInt(paper.getTime().getStartTime().split(":")[0]) >=1 && Integer.parseInt(paper.getTime().getStartTime().split(":")[0]) <=5 ) {
                    paperstart.setText(paper.getTitle());
                    papervenue.setText(paper.getVenue());
                    paperend.setText(paper.getTime().displayTime());
                    root.addView(paper_view);
                    paper_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                            intent.putExtra("Paper", paper);
                            startActivity(intent);
                        }
                    });

                    paperAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_green_24dp));
                            addPaper.setTextColor(Color.parseColor("#0F9D57"));
                        }
                    });
                }
            }
        }
    }
}
