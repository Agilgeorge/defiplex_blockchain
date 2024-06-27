// SPDX-License-Identifier: MIT
pragma solidity ^0.8.9;

/**
 * @title IDefiFlexGovernanceContract
 * @dev Interface for the DefiFlexGovernanceContract.
 */
interface IDefiFlexGovernanceContract {

    /**
     * @dev Event emitted when a new loan proposal is created.
     * @param proposalId The ID of the proposal.
     * @param proposer The address of the proposer.
     */
    event LoanProposalCreated(uint256 indexed proposalId, address indexed proposer);

    /**
     * @dev Event emitted when a vote is cast on a loan proposal.
     * @param proposalId The ID of the proposal.
     * @param voter The address of the voter.
     * @param support Whether the vote is in support of the proposal.
     */
    event LoanProposalVoted(uint256 indexed proposalId, address indexed voter, bool support);

    /**
     * @dev Sets the minimum votes required for a proposal to be considered approved.
     * @param minimumVotes The minimum number of votes required.
     */
    function setMinimumVotesRequired(uint256 minimumVotes) external;

    /**
     * @dev Sets the voting period for proposals.
     * @param period The duration of the voting period in seconds.
     */
    function setVotingPeriod(uint256 period) external;

    /**
     * @dev Proposes a new loan request.
     * @param loanProposalId The ID of the loan proposal.
     * @return The ID of the created proposal.
     */
    function proposeLoanRequest(uint256 loanProposalId) external returns (uint256);

    /**
     * @dev Votes on a loan proposal.
     * @param proposalId The ID of the proposal to vote on.
     * @param support Whether the vote is in support of the proposal.
     */
    function vote(uint256 proposalId, bool support) external;

    /**
     * @dev Checks the approval status of a proposal.
     * @param proposalId The ID of the proposal to check.
     * @return Whether the proposal is approved.
     */
    function checkProposalApprovalStatus(uint256 proposalId) external view returns (bool);
}