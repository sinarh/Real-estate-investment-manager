package com.example.realestatemanager;

public record PortfolioStats(
        int ownedCount,
        int interestedCount,
        double totalMarketValue,
        double equityGain,
        double totalExpenses) {
}
