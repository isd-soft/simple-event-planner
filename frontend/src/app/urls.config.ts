export const BASE_URL: string = 'https://simple-event-planner.herokuapp.com';

export const SIGN_IN: string = '/login';
export const SIGN_IN_URL: string = BASE_URL + SIGN_IN;

export const SIGN_UP: string = '/register';
export const SING_UP_URL: string = BASE_URL + SIGN_UP;

export const MY_EVENTS: string = '/events/my-events';
export const MY_EVENTS_URL: string = BASE_URL + MY_EVENTS;

export const EVENTS: string = '/events';
export const EVENTS_URL: string = BASE_URL + EVENTS;

export const USERS: string = '/users';
export const USERS_URL: string = BASE_URL + USERS;

export const USERS_FULL: string = USERS + '/full';
export const USERS_FULL_URL: string = BASE_URL + USERS_FULL;

export const CATEGORIES: string = '/categories';
export const CATEGORIES_URL: string = BASE_URL + CATEGORIES;

export const FILES: string = '/events/files';
export const FILES_URL: string = BASE_URL + FILES;

export const APPROVE_EVENT: string = EVENTS + '/approve-event';
export const APPROVE_EVENT_URL: string = BASE_URL + APPROVE_EVENT;

export const APPROVED_EVENTS: string = EVENTS + '/approved';
export const APPROVED_EVENTS_URL: string = BASE_URL + APPROVED_EVENTS;

export const STATISTICS: string = '/statistics';
export const STATISTICS_URL: string = BASE_URL + STATISTICS;

export const COMMENTS: string = '/comments';
export const COMMENTS_URL: string = BASE_URL + COMMENTS;

export const EVENT_REACTION: string = '/reactions/events';
export const EVENT_REACTION_URL: string = BASE_URL + EVENT_REACTION;

export const COMMENT_REACTION: string = '/reactions/comments';
export const COMMENT_REACTION_URL: string = BASE_URL + COMMENT_REACTION;

export const HEALTH_STATUS: string = '/actuator/health'
export const HEALTH_INFO: string = '/actuator/info'
export const HEALTH_STATUS_URL: string = BASE_URL + HEALTH_STATUS;
export const HEALTH_INFO_URL: string = BASE_URL + HEALTH_INFO;

